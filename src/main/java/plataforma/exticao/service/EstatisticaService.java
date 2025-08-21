package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import plataforma.exticao.repository.DenunciaRepository;
import plataforma.exticao.repository.SeresRepository;
import plataforma.exticao.repository.UsuarioRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EstatisticaService {

    private final DenunciaRepository denunciaRepository;
    private final SeresRepository seresRepository;
    private final UsuarioRepository usuarioRepository;

    public EstatisticaService(DenunciaRepository denunciaRepository,
                              SeresRepository seresRepository,
                              UsuarioRepository usuarioRepository) {
        this.denunciaRepository = denunciaRepository;
        this.seresRepository = seresRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Long contarDenuncias() {
        return denunciaRepository.count();
    }

    public Map<String, Long> contarDenunciasPorStatus() {
        return denunciaRepository.findAll().stream()
                .collect(Collectors.groupingBy(d -> d.getStatusAprovacao().name(), Collectors.counting()));
    }

    public Long contarSeres() {
        return seresRepository.count();
    }

    public Map<String, Long> contarSeresPorStatus() {
        return seresRepository.findAll().stream()
                .collect(Collectors.groupingBy(s -> s.getStatusConservacao().name(), Collectors.counting()));
    }

    public Long contarUsuarios() {
        return usuarioRepository.count();
    }
}
