package br.com.quickq.quickq_api.dto;

public record CadastroUsuarioDTO(String nome, String email, String hashSenha, String perfil) {
}
