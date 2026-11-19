package br.com.caminholivre.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caminholivre.entity.Usuario;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}