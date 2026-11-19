package br.com.caminholivre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caminholivre.entity.Estabelecimento;

public interface EstabelecimentoRepository
        extends JpaRepository<Estabelecimento, Long> {
}