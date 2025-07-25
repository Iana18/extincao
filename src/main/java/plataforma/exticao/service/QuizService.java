package plataforma.exticao.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import plataforma.exticao.model.Quiz;
import plataforma.exticao.repository.QuizRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private PerguntaService perguntaService;

    public List<Quiz> listarTodos() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> buscarPorId(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz salvar(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void deletar(Long id) {
        quizRepository.deleteById(id);
    }

    // Método para obter quiz com perguntas aleatórias limitadas
    public Optional<Quiz> buscarQuizComPerguntasAleatorias(Long quizId, int quantidadePerguntas) {
        Optional<Quiz> quizOpt = quizRepository.findById(quizId);
        if (quizOpt.isPresent()) {
            Quiz quiz = quizOpt.get();
            var perguntasAleatorias = perguntaService.buscarPerguntasAleatorias(quizId, quantidadePerguntas);
            quiz.setPerguntas(perguntasAleatorias);
            return Optional.of(quiz);
        }
        return Optional.empty();
    }
}

