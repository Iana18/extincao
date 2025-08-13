package plataforma.exticao.dtos;

import plataforma.exticao.model.UserRole;

public record RegisterDTO(
        String login,
        String password,
        UserRole role,
        String nomeCompleto,
        String email) {


    }
