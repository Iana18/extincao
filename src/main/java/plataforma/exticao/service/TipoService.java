package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import plataforma.exticao.model.Tipo;
import plataforma.exticao.repository.TipoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    // Retorna todos os tipos
    public List<Tipo> findAll() {
        return tipoRepository.findAll();
    }

    // Retorna um tipo por ID
    public Optional<Tipo> findById(Long id) {
        return tipoRepository.findById(id);
    }

    // Salva um novo tipo
    public Tipo save(Tipo tipo) {
        return tipoRepository.save(tipo);
    }

    // Atualiza um tipo existente
    public Tipo update(Long id, Tipo tipo) {
        try {
            return tipoRepository.findById(id).map(t -> {
                t.setNome(tipo.getNome());
                return tipoRepository.save(t); // Hibernate gerencia o versionamento
            }).orElseThrow(() -> new RuntimeException("Tipo não encontrado"));
        } catch (OptimisticLockingFailureException e) {
            throw new RuntimeException(
                    "Falha na atualização: o registro foi alterado por outro usuário. Tente novamente."
            );
        }
    }

    // Deleta um tipo por ID
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
}
