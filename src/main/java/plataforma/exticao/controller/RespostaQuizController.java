package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.model.RespostaQuiz;
import plataforma.exticao.service.RespostaQuizService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/respostas")
//@CrossOrigin(origins = "*")
public class RespostaQuizController {

    @Autowired
    private RespostaQuizService respostaQuizService;

    @GetMapping
    public List<RespostaQuiz> listarTodos() {
        return respostaQuizService.listarTodos();
    }

    @GetMapping("/pergunta/Id")
    public List<RespostaQuiz> listarPorPergunta(@PathVariable Long perguntaId) {
        return respostaQuizService.listarPorPerguntaId(perguntaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaQuiz> buscarPorId(@PathVariable Long id) {
        Optional<RespostaQuiz> resposta = respostaQuizService.buscarPorId(id);
        return resposta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/criar")
    public ResponseEntity<RespostaQuiz> criarResposta(@RequestBody RespostaQuiz respostaQuiz) {
        RespostaQuiz salva = respostaQuizService.salvar(respostaQuiz);
        return ResponseEntity.ok(salva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarResposta(@PathVariable Long id) {
        respostaQuizService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
