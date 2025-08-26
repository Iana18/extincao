package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.model.Quiz;
import plataforma.exticao.service.QuizService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
//@CrossOrigin(origins = "*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public List<Quiz> listarTodos() {
        return quizService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> buscarPorId(@PathVariable Long id) {
        Optional<Quiz> quiz = quizService.buscarPorId(id);
        return quiz.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/criar")
    public ResponseEntity<Quiz> criarQuiz(@RequestBody Quiz quiz) {
        Quiz novoQuiz = quizService.salvar(quiz);
        return ResponseEntity.ok(novoQuiz);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarQuiz(@PathVariable Long id) {
        quizService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obter quiz com perguntas aleatórias (quantidade parametrizável)
    @GetMapping("/{id}/perguntas-aleatorias")
    public ResponseEntity<Quiz> buscarQuizComPerguntasAleatorias(
            @PathVariable Long id,
            @RequestParam(defaultValue = "5") int quantidade) {
        Optional<Quiz> quiz = quizService.buscarQuizComPerguntasAleatorias(id, quantidade);
        return quiz.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Novo endpoint para jogar quiz
    @PostMapping("/{id}/jogar")
    public ResponseEntity<?> jogarQuiz(
            @PathVariable Long id,
            @RequestBody List<Long> respostasSelecionadas) {
        var resultado = quizService.jogarQuiz(id, respostasSelecionadas);
        return resultado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
