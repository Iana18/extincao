package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.dtos.EspecieRequestDTO;
import plataforma.exticao.model.*;
import plataforma.exticao.repository.EspecieRepository;
import plataforma.exticao.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class EspecieService {

    private final EspecieRepository especieRepository;
    private final UsuarioRepository usuarioRepository;

    public EspecieService(EspecieRepository especieRepository, UsuarioRepository usuarioRepository) {
        this.especieRepository = especieRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Especie registrar(EspecieRequestDTO dto) {
        if (dto.getLatitude() == null || dto.getLongitude() == null) {
            throw new IllegalArgumentException("Localização (latitude e longitude) é obrigatória.");
        }

        Usuario usuario = usuarioRepository.findById(dto.getRegistradoPorId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + dto.getRegistradoPorId()));

        Especie especie = new Especie();
        especie.setNomeComum(dto.getNomeComum());
        especie.setNomeCientifico(dto.getNomeCientifico());
        especie.setTipo(dto.getTipo());
        especie.setDescricao(dto.getDescricao());
        especie.setStatusConservacao(dto.getStatusConservacao());
        especie.setImagem(dto.getImagem());
        especie.setLatitude(dto.getLatitude());
        especie.setLongitude(dto.getLongitude());
        especie.setRegistradoPor(usuario);
        especie.setDataRegistro(LocalDateTime.now());
        especie.setStatusAprovacao(StatusAprovacao.PENDENTE);

        return especieRepository.save(especie);
    }

    public Especie registrarMultipart(
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

        Especie especie = new Especie();
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
    }

    public Especie aprovar(Long id, Especie dadosAprovacao) {
        Especie especie = especieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Espécie não encontrada com ID: " + id));

        especie.setStatusAprovacao(StatusAprovacao.APROVADO);
        especie.setDataAprovacao(LocalDateTime.now());
        especie.setAprovadoPor(dadosAprovacao.getAprovadoPor());

        return especieRepository.save(especie);
    }

    public List<Especie> listarTodas() {
        return especieRepository.findAll();
    }

    public List<Especie> listarPendentes() {
        return especieRepository.findByStatusAprovacao(StatusAprovacao.PENDENTE);
    }

    public Optional<Especie> buscarPorId(Long id) {
        return especieRepository.findById(id);
    }

    public List<Especie> buscarPorNome(String nome) {
        return especieRepository.findByNomeComumContainingIgnoreCase(nome);
    }

    public List<Especie> buscarPorTipo(TipoEspecie tipo) {
        return especieRepository.findByTipo(tipo);
    }

    public List<Especie> buscarPorStatusConservacao(StatusConservacao status) {
        return especieRepository.findByStatusConservacao(status);
    }

    public List<Especie> filtrar(String nomeComum, TipoEspecie tipo, StatusConservacao status) {
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
    public Especie atualizar(Long id, EspecieRequestDTO dto, String usuarioIdAutenticado) {
        Usuario usuario = usuarioRepository.findById(usuarioIdAutenticado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + usuarioIdAutenticado));

        if (usuario.getRole() != UserRole.ADMIN) {
            throw new SecurityException("Usuário não autorizado a atualizar espécies.");
        }

        Especie especie = especieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Espécie não encontrada com ID: " + id));

        especie.setNomeComum(dto.getNomeComum());
        especie.setNomeCientifico(dto.getNomeCientifico());
        especie.setTipo(dto.getTipo());
        especie.setDescricao(dto.getDescricao());
        especie.setStatusConservacao(dto.getStatusConservacao());
        especie.setImagem(dto.getImagem());
        especie.setLatitude(dto.getLatitude());
        especie.setLongitude(dto.getLongitude());

        return especieRepository.save(especie);
    }

    // Deletar espécie (somente admin)
    public void deletar(Long id, String usuarioIdAutenticado) {
        Usuario usuario = usuarioRepository.findById(usuarioIdAutenticado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + usuarioIdAutenticado));

        if (usuario.getRole() != UserRole.ADMIN) {
            throw new SecurityException("Usuário não autorizado a deletar espécies.");
        }

        Especie especie = especieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Espécie não encontrada com ID: " + id));

        especieRepository.delete(especie);
    }
}
