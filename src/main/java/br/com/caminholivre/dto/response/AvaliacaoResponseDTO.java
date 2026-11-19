package br.com.caminholivre.dto.response;

import java.time.LocalDateTime;

public record AvaliacaoResponseDTO(
    Long id,
    Integer nota,
    String comentario,
    LocalDateTime dataAvaliacao,
    Long usuarioId,
    Long estabelecimentoId
) {}