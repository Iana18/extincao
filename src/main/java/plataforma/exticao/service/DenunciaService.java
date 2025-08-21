package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.dtos.DenunciaCreateRequestDTO;
import plataforma.exticao.dtos.DenunciaResponseDTO;
import plataforma.exticao.dtos.UsuarioResponseDTO;
import plataforma.exticao.model.*;
import plataforma.exticao.repository.DenunciaRepository;
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

    public DenunciaService(DenunciaRepository denunciaRepository, UsuarioRepository usuarioRepository) {
        this.denunciaRepository = denunciaRepository;
        this.usuarioRepository = usuarioRepository;
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
            Seres especie = new Seres();
            especie.setId(especieId);  // ⚡ apenas setar o ID
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

    public Denuncia buildDenunciaFromDTO(DenunciaCreateRequestDTO dto, Usuario denunciante) {
        Denuncia denuncia = new Denuncia();
        denuncia.setTitulo(dto.getTitulo());
        denuncia.setDescricao(dto.getDescricao());
        denuncia.setDenunciadoPor(denunciante);

        if (dto.getEspecieId() != null) {
            Seres especie = new Seres();
            especie.setId(dto.getEspecieId()); // ⚡ apenas setar o ID
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

    public Denuncia aprovarDenuncia(Long id, Usuario usuarioAutenticado) {
        return atualizarStatus(id, StatusAprovacao.APROVADO, usuarioAutenticado);
    }

    public Denuncia rejeitarDenuncia(Long id, Usuario usuarioAutenticado) {
        return atualizarStatus(id, StatusAprovacao.REJEITADA, usuarioAutenticado);
    }

    public Denuncia resolverDenuncia(Long id, Usuario usuarioAutenticado) {
        return atualizarStatus(id, StatusAprovacao.RESOLVIDA, usuarioAutenticado);
    }

    // ------------------------ DELETE ------------------------
    public void deletar(Long id, Usuario usuarioAutenticado) {
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
        dto.setStatusAprovacao(denuncia.getStatusAprovacao());
        dto.setDataDenuncia(denuncia.getDataDenuncia());
        dto.setDataAprovacao(denuncia.getDataAprovacao());
        dto.setDataResolucao(denuncia.getDataResolucao());
        dto.setImagem(denuncia.getImagem());
        dto.setLatitude(denuncia.getLatitude());
        dto.setLongitude(denuncia.getLongitude());
        dto.setEspecieId(denuncia.getEspecie() != null ? denuncia.getEspecie().getId() : null);

        if (denuncia.getDenunciadoPor() != null) {
            dto.setDenunciadoPor(new UsuarioResponseDTO(
                    denuncia.getDenunciadoPor().getId(),
                    denuncia.getDenunciadoPor().getLogin(),
                    denuncia.getDenunciadoPor().getEmail(),
                    denuncia.getDenunciadoPor().getNomeCompleto(),
                    denuncia.getDenunciadoPor().getRole()
            ));
        }

        if (denuncia.getAprovadoPor() != null) {
            dto.setAprovadoPor(new UsuarioResponseDTO(
                    denuncia.getAprovadoPor().getId(),
                    denuncia.getAprovadoPor().getLogin(),
                    denuncia.getAprovadoPor().getEmail(),
                    denuncia.getAprovadoPor().getNomeCompleto(),
                    denuncia.getAprovadoPor().getRole()
            ));
        }

        return dto;
    }

    private void validarUsuarioAdmin(Usuario usuario) {
        if (usuario.getRole() != UserRole.ADMIN) {
            throw new SecurityException("Usuário não autorizado");
        }
    }
}
