package br.com.caminholivre.dto.response;

public record AuthResponseDTO(
    String token,
    String tipo,
    Long usuarioId,
    String nome,
    String email,
    String papel
) {
}