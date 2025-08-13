package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Qualquer usuário pode listar todas as perguntas
    @GetMapping
    public List<Pergunta> listarTodos() {
        return perguntaService.listarTodos();
    }

    // Qualquer usuário pode listar perguntas por quiz
    @GetMapping("/quiz/{quizId}")
    public List<Pergunta> listarPorQuiz(@PathVariable Long quizId) {
        return perguntaService.listarPorQuizId(quizId);
    }

    // Qualquer usuário pode buscar pergunta por id
    @GetMapping("/{id}")
    public ResponseEntity<Pergunta> buscarPorId(@PathVariable Long id) {
        Optional<Pergunta> pergunta = perguntaService.buscarPorId(id);
        return pergunta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Somente admin pode criar perguntas
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/criar")
    public ResponseEntity<?> criarPergunta(@RequestBody Pergunta pergunta) {
        try {
            Pergunta salva = perguntaService.salvar(pergunta);
            return ResponseEntity.ok(salva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Somente admin pode atualizar perguntas
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPergunta(@PathVariable Long id, @RequestBody Pergunta perguntaAtualizada) {
        try {
            Pergunta atualizada = perguntaService.atualizar(id, perguntaAtualizada);
            return ResponseEntity.ok(atualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Somente admin pode deletar perguntas
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPergunta(@PathVariable Long id) {
        perguntaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
