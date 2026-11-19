package br.com.caminholivre.ai;

import br.com.caminholivre.enums.CategoriaDenuncia;

public record AnaliseAcessibilidadeIA(
    CategoriaDenuncia categoriaSugerida,
    String gravidade,
    String riscosIdentificados,
    String resumo
) {
}