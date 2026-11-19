package br.com.caminholivre.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter até 100 caracteres")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um email válido")
    @Size(max = 150)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, max = 72,
          message = "A senha deve ter entre 8 e 72 caracteres")
    private String senha;

    public UsuarioRequestDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
