package plataforma.exticao.service;

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

    private final SeresRepository especieRepository;
    private final UsuarioRepository usuarioRepository;

    public SeresService(SeresRepository especieRepository, UsuarioRepository usuarioRepository) {
        this.especieRepository = especieRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Seres registrar(SeresRequestDTO dto) {
        if (dto.getLatitude() == null || dto.getLongitude() == null) {
            throw new IllegalArgumentException("Localização (latitude e longitude) é obrigatória.");
        }

        Usuario usuario = usuarioRepository.findById(dto.getRegistradoPor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + dto.getRegistradoPor().getId()));

        Seres seres= new Seres(dto);

        return especieRepository.save(seres);
    }

    /*
    public Seres registrarMultipart(
            String nomeComum,
            String nomeCientifico,
            TipoEspecie tipo,
            String descricao,
            StatusConservacao statusConservacao,
            Double latitude,
            Double longitude,
            String registradoPorId,
            MultipartFile imagemFile
    ) {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Localização (latitude e longitude) é obrigatória.");
        }

        Usuario usuario = usuarioRepository.findById(registradoPorId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + registradoPorId));

        Seres especie = new Seres();
        especie.setNomeComum(nomeComum);
        especie.setNomeCientifico(nomeCientifico);
        especie.setTipo(tipo);
        especie.setDescricao(descricao);
        especie.setStatusConservacao(statusConservacao);
        especie.setLatitude(latitude);
        especie.setLongitude(longitude);
        especie.setRegistradoPor(usuario);
        especie.setDataRegistro(LocalDateTime.now());
        especie.setStatusAprovacao(StatusAprovacao.PENDENTE);

        try {
            String base64Imagem = Base64.getEncoder().encodeToString(imagemFile.getBytes());
            especie.setImagem(base64Imagem);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar a imagem: " + e.getMessage());
        }

        return especieRepository.save(especie);
    }*/

    public Seres aprovar(Long id, Seres dadosAprovacao) {
        Seres especie = especieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Espécie não encontrada com ID: " + id));

        especie.setStatusAprovacao(StatusAprovacao.APROVADO);
        especie.setDataAprovacao(LocalDateTime.now());
        especie.setAprovadoPor(dadosAprovacao.getAprovadoPor());

        return especieRepository.save(especie);
    }

    public List<Seres> listarTodas() {
        return especieRepository.findAll();
    }

    public List<Seres> listarPendentes() {
        return especieRepository.findByStatusAprovacao(StatusAprovacao.PENDENTE);
    }

    public Optional<Seres> buscarPorId(Long id) {
        return especieRepository.findById(id);
    }

    public List<Seres> buscarPorNome(String nome) {
        return especieRepository.findByNomeComumContainingIgnoreCase(nome);
    }

    public List<Seres> buscarPorTipo(TipoEspecie tipo) {
        return especieRepository.findByTipo(tipo);
    }

    public List<Seres> buscarPorStatusConservacao(StatusConservacao status) {
        return especieRepository.findByStatusConservacao(status);
    }

    public List<Seres> filtrar(String nomeComum, TipoEspecie tipo, StatusConservacao status) {
        if (nomeComum != null && !nomeComum.isEmpty() && tipo != null && status != null) {
            return especieRepository.findByNomeComumContainingIgnoreCaseAndTipoAndStatusConservacao(nomeComum, tipo, status);
        } else if (nomeComum != null && !nomeComum.isEmpty() && tipo != null) {
            return especieRepository.findByNomeComumContainingIgnoreCaseAndTipo(nomeComum, tipo);
        } else if (nomeComum != null && !nomeComum.isEmpty() && status != null) {
            return especieRepository.findByNomeComumContainingIgnoreCaseAndStatusConservacao(nomeComum, status);
        } else if (tipo != null && status != null) {
            return especieRepository.findByTipoAndStatusConservacao(tipo, status);
        } else if (nomeComum != null && !nomeComum.isEmpty()) {
            return especieRepository.findByNomeComumContainingIgnoreCase(nomeComum);
        } else if (tipo != null) {
            return especieRepository.findByTipo(tipo);
        } else if (status != null) {
            return especieRepository.findByStatusConservacao(status);
        } else {
            return especieRepository.findAll();
        }
    }

    // Atualizar espécie (somente admin)
    public Seres atualizar(Long id, SeresRequestDTO dto, String usuarioIdAutenticado) {
        Usuario usuario = usuarioRepository.findById(usuarioIdAutenticado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + usuarioIdAutenticado));

        if (usuario.getRole() != UserRole.ADMIN) {
            throw new SecurityException("Usuário não autorizado a atualizar espécies.");
        }

         especieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Espécie não encontrada com ID: " + id));

        Seres seres= new Seres(dto,id);
        return especieRepository.save(seres);
    }

    // Deletar espécie (somente admin)
    public void deletar(Long id, String usuarioIdAutenticado) {
        Usuario usuario = usuarioRepository.findById(usuarioIdAutenticado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + usuarioIdAutenticado));

        if (usuario.getRole() != UserRole.ADMIN) {
            throw new SecurityException("Usuário não autorizado a deletar espécies.");
        }

        Seres especie = especieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Espécie não encontrada com ID: " + id));

        especieRepository.delete(especie);
    }
}
