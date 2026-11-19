package br.com.caminholivre.dto.response;

import java.time.LocalDateTime;

import br.com.caminholivre.enums.CategoriaDenuncia;
import br.com.caminholivre.enums.StatusAnaliseIA;
import br.com.caminholivre.enums.StatusDenuncia;

public record DenunciaResponseDTO(
    Long id,
    String titulo,
    String descricao,
    Double latitude,
    Double longitude,
    String fotoUrl,
    CategoriaDenuncia categoria,
    StatusDenuncia status,
    String gravidade,
    String resumoIA,
    LocalDateTime dataCriacao,
    Long usuarioId,
    String riscosIA,
    StatusAnaliseIA statusAnaliseIA
) {}
