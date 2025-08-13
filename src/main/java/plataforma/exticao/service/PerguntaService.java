package plataforma.exticao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.exticao.model.Pergunta;
import plataforma.exticao.repository.PerguntaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PerguntaService {

    @Autowired
    private PerguntaRepository perguntaRepository;

    public List<Pergunta> listarTodos() {
        return perguntaRepository.findAll();
    }

    public List<Pergunta> listarPorQuizId(Long quizId) {
        return perguntaRepository.findByQuizId(quizId);
    }

    public Optional<Pergunta> buscarPorId(Long id) {
        return perguntaRepository.findById(id);
    }

    // Busca perguntas aleatórias limitadas por quantidade
    public List<Pergunta> buscarPerguntasAleatorias(Long quizId, int quantidade) {
        List<Pergunta> perguntas = perguntaRepository.findByQuizId(quizId);
        Collections.shuffle(perguntas);
        if (perguntas.size() > quantidade) {
            return perguntas.subList(0, quantidade);
        }
        return perguntas;
    }

    // Salvar com validação para 4 respostas e 1 correta
    public Pergunta salvar(Pergunta pergunta) {
        if (!validarPergunta(pergunta)) {
            throw new IllegalArgumentException("A pergunta deve ter 4 respostas, sendo apenas uma correta.");
        }
        return perguntaRepository.save(pergunta);
    }

    // Atualizar pergunta existente
    public Pergunta atualizar(Long id, Pergunta perguntaAtualizada) {
        Pergunta perguntaExistente = perguntaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pergunta não encontrada com ID: " + id));

        perguntaExistente.setTexto(perguntaAtualizada.getTexto());
        perguntaExistente.setQuiz(perguntaAtualizada.getQuiz());
        perguntaExistente.setRespostas(perguntaAtualizada.getRespostas());

        if (!validarPergunta(perguntaExistente)) {
            throw new IllegalArgumentException("A pergunta deve ter 4 respostas, sendo apenas uma correta.");
        }

        return perguntaRepository.save(perguntaExistente);
    }

    private boolean validarPergunta(Pergunta pergunta) {
        if (pergunta.getRespostas() == null || pergunta.getRespostas().size() != 4) {
            return false;
        }
        long countCorretas = pergunta.getRespostas().stream().filter(resposta -> resposta.isCorreta()).count();
        return countCorretas == 1;
    }

    public void deletar(Long id) {
        perguntaRepository.deleteById(id);
    }
}
