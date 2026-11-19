package br.com.caminholivre.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EstabelecimentoRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 150)
    private String nome;

    @Size(max = 1000)
    private String descricao;

    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 255)
    private String endereco;

    @NotNull
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;

    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;

    @Size(max = 2000)
    private String acessibilidade;

    public EstabelecimentoRequestDTO() {
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAcessibilidade() { return acessibilidade; }
    public void setAcessibilidade(String acessibilidade) {
        this.acessibilidade = acessibilidade;
    }
}
