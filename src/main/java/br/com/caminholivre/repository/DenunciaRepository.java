package br.com.caminholivre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caminholivre.entity.Denuncia;
import br.com.caminholivre.enums.StatusDenuncia;

public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {

    List<Denuncia> findByUsuarioId(Long usuarioId);

    List<Denuncia> findByStatus(StatusDenuncia status);
}