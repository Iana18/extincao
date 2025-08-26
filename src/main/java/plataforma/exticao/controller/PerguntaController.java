package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.dtos.PerguntaComRespostasDTO;
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

    // Listar todas as perguntas
    @GetMapping
    public List<Pergunta> listarTodos() {
        return perguntaService.listarTodos();
    }

    // Listar perguntas por quiz
    @GetMapping("/quiz/{quizId}")
    public List<Pergunta> listarPorQuiz(@PathVariable Long quizId) {
        return perguntaService.listarPorQuizId(quizId);
    }

    // Buscar pergunta por id
    @GetMapping("/{id}")
    public ResponseEntity<Pergunta> buscarPorId(@PathVariable Long id) {
        Optional<Pergunta> pergunta = perguntaService.buscarPorId(id);
        return pergunta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Criar pergunta simples (somente admin)
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

    // Criar pergunta com respostas via DTO (somente admin)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/criar-com-respostas")
    public ResponseEntity<?> criarPerguntaComRespostas(@RequestBody PerguntaComRespostasDTO dto) {
        try {
            Pergunta pergunta = perguntaService.salvarComRespostas(dto);
            return ResponseEntity.ok(pergunta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Atualizar pergunta (somente admin)
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

    // Deletar pergunta (somente admin)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPergunta(@PathVariable Long id) {
        perguntaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
