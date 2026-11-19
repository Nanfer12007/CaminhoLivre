package br.com.caminholivre.dto.response;

import br.com.caminholivre.enums.Papel;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email,
    Papel papel
) {}
