package plataforma.exticao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import plataforma.exticao.dtos.TipoRequestDTO;
import plataforma.exticao.dtos.TipoResponseDTO;
import plataforma.exticao.model.Especie;
import plataforma.exticao.model.Tipo;
import plataforma.exticao.repository.EspecieRepository;
import plataforma.exticao.repository.TipoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private EspecieRepository especieRepository;

    // Retorna todos os tipos
    public List<Tipo> findAll() {
        return tipoRepository.findAll();
    }

    // Retorna todos os tipos como DTOs (com nome da espécie)
    public List<TipoResponseDTO> findAllDTO() {
        return tipoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Retorna tipo por ID
    public Optional<Tipo> findById(Long id) {
        return tipoRepository.findById(id);
    }

    // Salva um novo tipo a partir do DTO
    public Tipo saveFromDTO(TipoRequestDTO dto) {
        Tipo tipo = new Tipo();
        tipo.setNome(dto.getNome());
        tipo.setDescricao(dto.getDescricao());

        if (dto.getEspecieId() != null) {
            Especie especie = especieRepository.findById(dto.getEspecieId())
                    .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
            tipo.setEspecie(especie);
        }

        return tipoRepository.save(tipo);
    }

    // Atualiza tipo existente
    public Tipo update(Long id, TipoRequestDTO dto) {
        try {
            return tipoRepository.findById(id).map(t -> {
                if (dto.getNome() != null) t.setNome(dto.getNome());
                if (dto.getDescricao() != null) t.setDescricao(dto.getDescricao());

                if (dto.getEspecieId() != null) {
                    Especie especie = especieRepository.findById(dto.getEspecieId())
                            .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
                    t.setEspecie(especie);
                }

                return tipoRepository.save(t);
            }).orElseThrow(() -> new RuntimeException("Tipo não encontrado"));
        } catch (OptimisticLockingFailureException e) {
            throw new RuntimeException(
                    "Falha na atualização: o registro foi alterado por outro usuário. Tente novamente."
            );
        }
    }

    // Deleta tipo
    public void delete(Long id) {
        if (tipoRepository.existsById(id)) {
            try {
                tipoRepository.deleteById(id);
            } catch (OptimisticLockingFailureException e) {
                throw new RuntimeException(
                        "Falha na exclusão: o registro foi alterado por outro usuário. Tente novamente."
                );
            }
        } else {
            throw new RuntimeException("Tipo não encontrado");
        }
    }

    // Converte Tipo para TipoResponseDTO
    public TipoResponseDTO toDTO(Tipo tipo) {
        String especieNome = tipo.getEspecie() != null ? tipo.getEspecie().getNome() : "—";
        return new TipoResponseDTO(
                tipo.getId(),
                tipo.getNome(),
                tipo.getDescricao(),
                especieNome
        );
    }

}
