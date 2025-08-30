package plataforma.exticao.dtos;

public class NotificacaoDTO {
    private String mensagem;
    private boolean global; // true = todos os usuários, false = usuário específico

    public NotificacaoDTO() {}
    public NotificacaoDTO(String mensagem, boolean global) {
        this.mensagem = mensagem;
        this.global = global;
    }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public boolean isGlobal() { return global; }
    public void setGlobal(boolean global) { this.global = global; }
}