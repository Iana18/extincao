package plataforma.exticao.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.dtos.SeresRequestDTO;
import plataforma.exticao.model.*;
import plataforma.exticao.repository.EspecieRepository;
import plataforma.exticao.repository.SeresRepository;
import plataforma.exticao.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeresService {

    private final SeresRepository seresRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecieRepository especieRepository;

    public SeresService(SeresRepository seresRepository, UsuarioRepository usuarioRepository,
                        EspecieRepository especieRepository) {
        this.seresRepository = seresRepository;
        this.usuarioRepository = usuarioRepository;
        this.especieRepository = especieRepository;
    }

    // ------------------------ CREATE ------------------------
    public Seres registrar(SeresRequestDTO dto) {
        validarLocalizacao(dto.getLatitude(), dto.getLongitude());

        Usuario usuario = dto.getRegistradoPor();
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário registrado é obrigatório.");
        }

        Seres seres = buildSeresFromDTO(dto, usuario);
        return seresRepository.save(seres);
    }

    public Seres registrarMultipart(String nomeComum, String nomeCientifico, Tipo tipo, Especie especie,
                                    String descricao, StatusConservacao statusConservacao, Double latitude,
                                    Double longitude, String usuarioLogin, String usuarioEmail,
                                    MultipartFile imagemFile, Categoria categoria) {

        validarLocalizacao(latitude, longitude);
        Usuario registradoPor = buscarUsuario(usuarioLogin, usuarioEmail);

        Seres seres = new Seres();
        seres.setNomeComum(nomeComum);
        seres.setNomeCientifico(nomeCientifico);
        seres.setTipo(tipo);
        seres.setEspecie(especie);
        seres.setCategoria(categoria);
        seres.setDescricao(descricao);
        seres.setStatusConservacao(statusConservacao);
        seres.setLatitude(latitude);
        seres.setLongitude(longitude);
        seres.setRegistradoPor(registradoPor);
        seres.setDataRegistro(LocalDateTime.now());
        seres.setStatusAprovacao(StatusAprovacao.PENDENTE);

        if (imagemFile != null && !imagemFile.isEmpty()) {
            try {
                String base64Imagem = Base64.getEncoder().encodeToString(imagemFile.getBytes());
                seres.setImagem(base64Imagem);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar a imagem: " + e.getMessage());
            }
        }

        return seresRepository.save(seres);
    }

    // ------------------------ READ ------------------------
    public List<Seres> listarTodas() {
        return seresRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeComum"));
    }

    public List<Seres> listarPendentes() {
        return seresRepository.findByStatusAprovacao(StatusAprovacao.PENDENTE);
    }

    public Optional<Seres> buscarPorId(Long id) {
        return seresRepository.findById(id);
    }

    // ------------------------ UPDATE ------------------------
    public Seres atualizar(Long id, SeresRequestDTO dto, Usuario usuarioAutenticado) {
        if (usuarioAutenticado.getRole() != UserRole.ADMIN) {
            throw new SecurityException("Usuário não autorizado a atualizar espécies.");
        }

        Seres existente = seresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Espécie não encontrada com ID: " + id));

        existente.setNomeComum(dto.getNomeComum());
        existente.setNomeCientifico(dto.getNomeCientifico());
        existente.setTipo(dto.getTipo());
        existente.setCategoria(dto.getEspecie() != null ? dto.getEspecie().getCategoria() : existente.getCategoria());
        existente.setDescricao(dto.getDescricao());
        existente.setStatusConservacao(dto.getStatusConservacao());
        existente.setLatitude(dto.getLatitude());
        existente.setLongitude(dto.getLongitude());

        // Atualizar imagem apenas se vier uma nova
        if (dto.getImagem() != null && !dto.getImagem().isEmpty()) {
            existente.setImagem(dto.getImagem());
        }

        return seresRepository.save(existente);
    }


    // ------------------------ DELETE ------------------------
    public void deletar(Long id, Usuario usuarioAutenticado) {
        if (usuarioAutenticado.getRole() != UserRole.ADMIN) {
            throw new SecurityException("Usuário não autorizado a deletar espécies.");
        }

        Seres seres = seresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Espécie não encontrada com ID: " + id));

        seresRepository.delete(seres);
    }

    // ------------------------ APPROVE ------------------------
    public Seres aprovar(Long id, Usuario aprovadoPor) {
        Seres seres = seresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Espécie não encontrada com ID: " + id));

        seres.setStatusAprovacao(StatusAprovacao.APROVADO);
        seres.setDataAprovacao(LocalDateTime.now());
        seres.setAprovadoPor(aprovadoPor);

        return seresRepository.save(seres);
    }

    // ------------------------ FILTRAGEM FLEXÍVEL ------------------------
    public List<Seres> filtrarSeres(
            String nomeComum,
            List<Especie> especies,
            StatusConservacao statusConservacao,
            StatusAprovacao statusAprovacao
    ) {
        Specification<Seres> spec = (root, query, cb) -> null; // ponto inicial "vazio"

        if (nomeComum != null && !nomeComum.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("nomeComum")), "%" + nomeComum.toLowerCase() + "%"));
        }

        if (especies != null && !especies.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("especie").in(especies));
        }

        if (statusConservacao != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("statusConservacao"), statusConservacao));
        }

        if (statusAprovacao != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("statusAprovacao"), statusAprovacao));
        }

        return seresRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "nomeComum"));
    }


    // ------------------------ HELPERS ------------------------
    public Usuario buscarUsuario(String login, String email) {
        if (login != null && !login.isBlank()) {
            return usuarioRepository.findByLogin(login)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com login: " + login));
        } else if (email != null && !email.isBlank()) {
            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));
        } else {
            throw new IllegalArgumentException("É necessário informar login ou email do usuário.");
        }
    }

    private Seres buildSeresFromDTO(SeresRequestDTO dto, Usuario registradoPor) {
        Seres seres = new Seres();
        seres.setNomeComum(dto.getNomeComum());
        seres.setNomeCientifico(dto.getNomeCientifico());
        seres.setTipo(dto.getTipo());
        seres.setCategoria(dto.getCategoria());
        seres.setDescricao(dto.getDescricao());
        seres.setStatusConservacao(dto.getStatusConservacao());
        seres.setLatitude(dto.getLatitude());
        seres.setLongitude(dto.getLongitude());
        seres.setRegistradoPor(registradoPor);
        seres.setDataRegistro(LocalDateTime.now());
        seres.setStatusAprovacao(StatusAprovacao.PENDENTE);
        if (dto.getImagem() != null) {
            seres.setImagem(dto.getImagem());
        }
        if (dto.getEspecie() != null) {
            seres.setEspecie(dto.getEspecie());
        }
        return seres;
    }

    private void validarLocalizacao(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Localização (latitude e longitude) é obrigatória.");
        }
    }
}
