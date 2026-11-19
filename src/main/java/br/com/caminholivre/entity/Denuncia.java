
package br.com.caminholivre.entity;

import java.time.LocalDateTime;

import br.com.caminholivre.enums.CategoriaDenuncia;
import br.com.caminholivre.enums.StatusAnaliseIA;
import br.com.caminholivre.enums.StatusDenuncia;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "denuncias")
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 2000)
    private String descricao;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String fotoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CategoriaDenuncia categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusDenuncia status;

    @Column(length = 30)
    private String gravidade;

    @Column(length = 2000)
    private String resumoIA;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Denuncia() {
    }
    
    @Column(columnDefinition = "TEXT")
    private String riscosIA;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusAnaliseIA statusAnaliseIA;

    @PrePersist
    public void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusDenuncia.ABERTA;
        }      
        if (statusAnaliseIA == null) {
            statusAnaliseIA = StatusAnaliseIA.PENDENTE;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public StatusDenuncia getStatus() {
        return status;
    }

    public void setStatus(StatusDenuncia status) {
        this.status = status;
    }

    public String getGravidade() {
        return gravidade;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    public String getResumoIA() {
        return resumoIA;
    }

    public void setResumoIA(String resumoIA) {
        this.resumoIA = resumoIA;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

	public String getRiscosIA() {
		// TODO Auto-generated method stub
		return null;
	}

	public StatusAnaliseIA getStatusAnaliseIA() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRiscosIA(String riscosIdentificados) {
		// TODO Auto-generated method stub
		
	}

	public void setStatusAnaliseIA(StatusAnaliseIA concluida) {
		// TODO Auto-generated method stub
		
	}
}
