package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.dtos.*;
import plataforma.exticao.model.*;
import plataforma.exticao.repository.DenunciaRepository;
import plataforma.exticao.repository.EspecieRepository;
import plataforma.exticao.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DenunciaService {

    private final DenunciaRepository denunciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecieRepository especieRepository;

    public DenunciaService(DenunciaRepository denunciaRepository, EspecieRepository especieRepository, UsuarioRepository usuarioRepository) {
        this.denunciaRepository = denunciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.especieRepository = especieRepository;
    }

    // ------------------------ CREATE ------------------------
    public Denuncia registrar(DenunciaCreateRequestDTO dto) {
        Usuario denunciante = usuarioRepository
                .findByLoginAndEmail(dto.getUsuarioLogin(), dto.getUsuarioEmail())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário não encontrado com login e email fornecidos"));

        Denuncia denuncia = buildDenunciaFromDTO(dto, denunciante);
        return denunciaRepository.save(denuncia);
    }

    public Denuncia registrarMultipart(String titulo, String descricao, Double latitude, Double longitude,
                                       Long especieId, String usuarioLogin, String usuarioEmail, MultipartFile imagemFile) {

        Usuario denunciante = usuarioRepository
                .findByLoginAndEmail(usuarioLogin, usuarioEmail)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário não encontrado com login e email fornecidos"));

        Denuncia denuncia = new Denuncia();
        denuncia.setTitulo(titulo);
        denuncia.setDescricao(descricao);
        denuncia.setLatitude(latitude);
        denuncia.setLongitude(longitude);
        denuncia.setDenunciadoPor(denunciante);
        denuncia.setDataDenuncia(LocalDateTime.now());
        denuncia.setStatusAprovacao(StatusAprovacao.PENDENTE);

        if (especieId != null) {
            Especie especie = especieRepository.findById(especieId)
                    .orElseThrow(() -> new RuntimeException("Espécie não encontrada com ID: " + especieId));
            denuncia.setEspecie(especie);
        }

        if (imagemFile != null && !imagemFile.isEmpty()) {
            try {
                String base64Imagem = Base64.getEncoder().encodeToString(imagemFile.getBytes());
                denuncia.setImagem(base64Imagem);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar a imagem: " + e.getMessage());
            }
        }

        return denunciaRepository.save(denuncia);
    }

    private Denuncia buildDenunciaFromDTO(DenunciaCreateRequestDTO dto, Usuario denunciante) {
        Denuncia denuncia = new Denuncia();
        denuncia.setTitulo(dto.getTitulo());
        denuncia.setDescricao(dto.getDescricao());
        denuncia.setDenunciadoPor(denunciante);

        if (dto.getEspecieId() != null) {
            Especie especie = especieRepository.findById(dto.getEspecieId())
                    .orElseThrow(() -> new RuntimeException("Espécie não encontrada com ID: " + dto.getEspecieId()));
            denuncia.setEspecie(especie);
        }

        denuncia.setLatitude(dto.getLatitude());
        denuncia.setLongitude(dto.getLongitude());
        denuncia.setDataDenuncia(LocalDateTime.now());
        denuncia.setStatusAprovacao(StatusAprovacao.PENDENTE);

        return denuncia;
    }

    // ------------------------ READ ------------------------
    public List<DenunciaResponseDTO> listarTodas() {
        return denunciaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<DenunciaResponseDTO> listarPendentes() {
        return denunciaRepository.findByStatusAprovacao(StatusAprovacao.PENDENTE).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DenunciaResponseDTO> buscarPorId(Long id) {
        return denunciaRepository.findById(id).map(this::mapToDTO);
    }

    public List<DenunciaResponseDTO> buscarPorTituloOuDescricao(String termo) {
        return denunciaRepository.findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(termo, termo)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ------------------------ UPDATE STATUS ------------------------
    public Denuncia atualizarStatus(Long id, StatusAprovacao novoStatus, Usuario usuarioAutenticado) {
        validarUsuarioAdmin(usuarioAutenticado);

        Denuncia denuncia = denunciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Denúncia não encontrada com ID: " + id));

        if (novoStatus == StatusAprovacao.APROVADO || novoStatus == StatusAprovacao.REJEITADA) {
            denuncia.setDataAprovacao(LocalDateTime.now());
            denuncia.setAprovadoPor(usuarioAutenticado);
        }

        if (novoStatus == StatusAprovacao.RESOLVIDA) {
            denuncia.setDataResolucao(LocalDateTime.now());
            denuncia.setAprovadoPor(usuarioAutenticado);
        }

        denuncia.setStatusAprovacao(novoStatus);
        return denunciaRepository.save(denuncia);
    }

    // ------------------------ UPDATE DENUNCIA ------------------------
    public Denuncia atualizar(Long id, DenunciaUpdateRequestDTO dto, Usuario usuario) {
        Denuncia denuncia = denunciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada"));

        // Verifica se o usuário é o dono ou tem permissão
        if (!denuncia.getDenunciadoPor().equals(usuario)) {
            throw new RuntimeException("Usuário não autorizado para atualizar esta denúncia");
        }

        if (dto.getTitulo() != null) {
            denuncia.setTitulo(dto.getTitulo());
        }

        if (dto.getDescricao() != null) {
            denuncia.setDescricao(dto.getDescricao());
        }

        if (dto.getEspecie() != null) {
            // Busca a espécie pelo nome (retorna Optional)
            Especie especieAtualizada = especieRepository.findByNomeIgnoreCase(dto.getEspecie().getNome())
                    .orElseThrow(() -> new RuntimeException(
                            "Espécie não encontrada: " + dto.getEspecie().getNome()));

            denuncia.setEspecie(especieAtualizada);
        }

        return denunciaRepository.save(denuncia);
    }

    // ------------------------ DELETE ------------------------
    public void deletar(Long id, String login) {
        Usuario usuarioAutenticado = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        validarUsuarioAdmin(usuarioAutenticado);

        Denuncia denuncia = denunciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Denúncia não encontrada com ID: " + id));

        denunciaRepository.delete(denuncia);
    }

    // ------------------------ HELPERS ------------------------
    public DenunciaResponseDTO mapToDTO(Denuncia denuncia) {
        DenunciaResponseDTO dto = new DenunciaResponseDTO();

        dto.setId(denuncia.getId());
        dto.setTitulo(denuncia.getTitulo());
        dto.setDescricao(denuncia.getDescricao());
        dto.setImagem(denuncia.getImagem());
        dto.setStatusAprovacao(denuncia.getStatusAprovacao());
        dto.setDataDenuncia(denuncia.getDataDenuncia());
        dto.setDataAprovacao(denuncia.getDataAprovacao());
        dto.setDataResolucao(denuncia.getDataResolucao());
        dto.setLatitude(denuncia.getLatitude());
        dto.setLongitude(denuncia.getLongitude());

        if (denuncia.getEspecie() != null) {
            Especie especie = denuncia.getEspecie();
            EspecieRequestDTO especieDto = new EspecieRequestDTO();
            especieDto.setId(especie.getId());
            if (especie.getCategoria() != null) {
                especieDto.setCategoriaId(especie.getCategoria().getId());
            }
            especieDto.setNome(especie.getNome());
            especieDto.setDescricao(especie.getDescricao());
            dto.setEspecie(especieDto);
        }

        if (denuncia.getDenunciadoPor() != null) {
            Usuario u = denuncia.getDenunciadoPor();
            dto.setDenunciadoPor(new UsuarioResponseDTO(
                    u.getId(), u.getLogin(), u.getEmail(),
                    u.getNomeCompleto(), u.getRole(),
                    u.getNivelAtual(), u.getQuizzesCompletos()
            ));
        }

        if (denuncia.getAprovadoPor() != null) {
            Usuario u = denuncia.getAprovadoPor();
            dto.setAprovadoPor(new UsuarioResponseDTO(
                    u.getId(), u.getLogin(), u.getEmail(),
                    u.getNomeCompleto(), u.getRole(),
                    u.getNivelAtual(), u.getQuizzesCompletos()
            ));
        }

        return dto;
    }


    // ------------------------ STATUS HELPERS ------------------------
    public Denuncia aprovarDenuncia(Long id, Usuario usuarioAutenticado) {
        return atualizarStatus(id, StatusAprovacao.APROVADO, usuarioAutenticado);
    }

    public Denuncia rejeitarDenuncia(Long id, Usuario usuarioAutenticado) {
        return atualizarStatus(id, StatusAprovacao.REJEITADA, usuarioAutenticado);
    }

    public Denuncia resolverDenuncia(Long id, Usuario usuarioAutenticado) {
        return atualizarStatus(id, StatusAprovacao.RESOLVIDA, usuarioAutenticado);
    }

    private void validarUsuarioAdmin(Usuario usuario) {
        if (usuario.getRole() != UserRole.ADMIN) {
            throw new SecurityException("Usuário não autorizado. Apenas ADMIN pode executar esta ação.");
        }
    }
}
