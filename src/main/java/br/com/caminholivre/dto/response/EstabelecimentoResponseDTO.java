package br.com.caminholivre.dto.response;

public record EstabelecimentoResponseDTO(
    Long id,
    String nome,
    String descricao,
    String endereco,
    Double latitude,
    Double longitude,
    String acessibilidade
) {}
