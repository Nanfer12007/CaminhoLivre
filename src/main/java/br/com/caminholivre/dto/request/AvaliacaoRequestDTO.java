package br.com.caminholivre.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AvaliacaoRequestDTO {

    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota mínima é 1")
    @Max(value = 5, message = "A nota máxima é 5")
    private Integer nota;

    @Size(max = 1000)
    private String comentario;

    @NotNull(message = "O estabelecimento é obrigatório")
    private Long estabelecimentoId;

    public AvaliacaoRequestDTO() {
    }

    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getEstabelecimentoId() {
        return estabelecimentoId;
    }

    public void setEstabelecimentoId(Long estabelecimentoId) {
        this.estabelecimentoId = estabelecimentoId;
    }
}
