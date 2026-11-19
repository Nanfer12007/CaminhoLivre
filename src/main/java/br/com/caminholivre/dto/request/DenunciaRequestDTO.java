package br.com.caminholivre.dto.request;

import br.com.caminholivre.enums.CategoriaDenuncia;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DenunciaRequestDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 150)
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 2000)
    private String descricao;

    @NotNull(message = "A latitude é obrigatória")
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private Double latitude;

    @NotNull(message = "A longitude é obrigatória")
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private Double longitude;

    @Size(max = 500)
    private String fotoUrl;

    @NotNull(message = "A categoria é obrigatória")
    private CategoriaDenuncia categoria;

    public DenunciaRequestDTO() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public CategoriaDenuncia getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDenuncia categoria) {
        this.categoria = categoria;
    }
}
