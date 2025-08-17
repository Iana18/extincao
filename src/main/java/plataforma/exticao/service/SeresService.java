package plataforma.exticao.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.dtos.SeresRequestDTO;
import plataforma.exticao.model.*;
import plataforma.exticao.repository.SeresRepository;
import plataforma.exticao.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class SeresService {

    private final SeresRepository seresRepository;
    private final UsuarioRepository usuarioRepository; // necessário para buscar usuário

    public SeresService(SeresRepository seresRepository, UsuarioRepository usuarioRepository) {
        this.seresRepository = seresRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // ------------------------ CREATE ------------------------
    public Seres registrar(SeresRequestDTO dto) {
        validarLocalizacao(dto.getLatitude(), dto.getLongitude());

        Usuario usuario = buscarUsuario(dto.getUsuarioLogin(), dto.getUsuarioEmail());
        Seres seres = buildSeresFromDTO(dto, usuario);

        return seresRepository.save(seres);
    }

    public Seres registrarMultipart(String nomeComum, String nomeCientifico, Tipo tipo, Especie especie,
                                    String descricao, StatusConservacao statusConservacao, Double latitude,
                                    Double longitude, String usuarioLogin, String usuarioEmail, MultipartFile imagemFile, Categoria categoria) {

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
        return seresRepository.findAll();
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
        existente.setCategoria(dto.getCategoria());
        existente.setDescricao(dto.getDescricao());
        existente.setStatusConservacao(dto.getStatusConservacao());
        existente.setLatitude(dto.getLatitude());
        existente.setLongitude(dto.getLongitude());

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
    public List<Seres> filtrarSeres(String nomeComum, Especie especie, StatusConservacao status) {
        boolean temNome = nomeComum != null && !nomeComum.isEmpty();
        boolean temEspecie = especie != null;
        boolean temStatus = status != null;

        if (temNome && temEspecie && temStatus) {
            return seresRepository.findByNomeComumContainingIgnoreCaseAndEspecieAndStatusConservacao(nomeComum, especie, status);
        } else if (temNome && temEspecie) {
            return seresRepository.findByNomeComumContainingIgnoreCaseAndEspecie(nomeComum, especie);
        } else if (temNome && temStatus) {
            return seresRepository.findByNomeComumContainingIgnoreCaseAndStatusConservacao(nomeComum, status);
        } else if (temEspecie && temStatus) {
            return seresRepository.findByEspecieAndStatusConservacao(especie, status);
        } else if (temNome) {
            return seresRepository.findByNomeComumContainingIgnoreCase(nomeComum);
        } else if (temEspecie) {
            return seresRepository.findByEspecie(especie);
        } else if (temStatus) {
            return seresRepository.findByStatusConservacao(status);
        } else {
            return seresRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeComum"));
        }
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
