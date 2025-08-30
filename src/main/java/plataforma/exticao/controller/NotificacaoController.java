package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.service.NotificacaoService;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    // Endpoint de teste para enviar notificação a um usuário
    @PostMapping("/enviar")
    public ResponseEntity<String> enviarNotificacao(
            @RequestParam Long usuarioId,
            @RequestParam String mensagem
    ) {
        notificacaoService.enviarParaUsuario(usuarioId, mensagem);
        return ResponseEntity.ok("Notificação enviada para usuário " + usuarioId);
    }
}