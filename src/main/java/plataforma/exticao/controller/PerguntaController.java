package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.model.Pergunta;
import plataforma.exticao.service.PerguntaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/perguntas")
//@CrossOrigin(origins = "*")
public class PerguntaController {

    @Autowired
    private PerguntaService perguntaService;

    @GetMapping
    public List<Pergunta> listarTodos() {
        return perguntaService.listarTodos();
    }

    @GetMapping("/quiz/{quizId}")
    public List<Pergunta> listarPorQuiz(@PathVariable Long quizId) {
        return perguntaService.listarPorQuizId(quizId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pergunta> buscarPorId(@PathVariable Long id) {
        Optional<Pergunta> pergunta = perguntaService.buscarPorId(id);
        return pergunta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criarPergunta(@RequestBody Pergunta pergunta) {
        try {
            Pergunta salva = perguntaService.salvar(pergunta);
            return ResponseEntity.ok(salva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPergunta(@PathVariable Long id) {
        perguntaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
