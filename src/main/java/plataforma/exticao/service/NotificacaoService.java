package plataforma.exticao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import plataforma.exticao.dtos.NotificacaoDTO;

@Service
public class NotificacaoService {

    @Autowired
    private SimpMessagingTemplate template;

    // Envia para um usuário específico
    public void enviarParaUsuario(Long usuarioId, String mensagem) {
        // Para simplificar, apenas log
        System.out.println("Notificação para usuário " + usuarioId + ": " + mensagem);

        // TODO: implementar persistência ou envio real (WebSocket, push, email)
    }
    // Envia para todos os usuários
    public void enviarGlobal(String mensagem) {
        template.convertAndSend("/topic/notificacoes/global", new NotificacaoDTO(mensagem, true));
    }
}