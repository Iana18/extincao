package plataforma.exticao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plataforma.exticao.dtos.JogoResultadoDTO;
import plataforma.exticao.model.Pergunta;
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

    // Obter quiz com perguntas aleatórias
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

    // Lógica do jogo
    public Optional<JogoResultadoDTO> jogarQuiz(Long quizId, List<Long> respostasSelecionadas) {
        Optional<Quiz> quizOpt = quizRepository.findById(quizId);
        if (quizOpt.isEmpty()) {
            return Optional.empty();
        }

        Quiz quiz = quizOpt.get();
        int acertos = 0;

        for (Pergunta pergunta : quiz.getPerguntas()) {
            boolean acertou = pergunta.getRespostas().stream()
                    .anyMatch(r -> r.isCorreta() && respostasSelecionadas.contains(r.getId()));
            if (acertou) {
                acertos++;
            }
        }

        boolean passou = acertos == quiz.getPerguntas().size();
        int proximoNivel = passou ? quiz.getNivel() + 1 : quiz.getNivel();

        return Optional.of(new JogoResultadoDTO(
                quiz.getId(),
                quiz.getNivel(),
                acertos,
                quiz.getPerguntas().size(),
                passou,
                passou ? proximoNivel : null
        ));
    }
}
