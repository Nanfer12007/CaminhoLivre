package br.com.caminholivre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caminholivre.entity.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByEstabelecimentoId(Long estabelecimentoId);

    boolean existsByUsuarioIdAndEstabelecimentoId(
        Long usuarioId,
        Long estabelecimentoId
    );
}