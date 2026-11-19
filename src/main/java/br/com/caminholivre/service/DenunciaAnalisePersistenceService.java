package br.com.caminholivre.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.caminholivre.ai.AnaliseAcessibilidadeIA;
import br.com.caminholivre.entity.Denuncia;
import br.com.caminholivre.enums.StatusAnaliseIA;
import br.com.caminholivre.exception.ResourceNotFoundException;
import br.com.caminholivre.repository.DenunciaRepository;

@Service
public class DenunciaAnalisePersistenceService {

    private final DenunciaRepository repository;

    public DenunciaAnalisePersistenceService(
            DenunciaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void salvarResultado(
            Long denunciaId,
            AnaliseAcessibilidadeIA analise) {

        Denuncia denuncia = repository.findById(denunciaId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Denúncia não encontrada"
                )
            );

        denuncia.setCategoria(analise.categoriaSugerida());
        denuncia.setGravidade(analise.gravidade());
        denuncia.setRiscosIA(analise.riscosIdentificados());
        denuncia.setResumoIA(analise.resumo());

        denuncia.setStatusAnaliseIA(
            StatusAnaliseIA.CONCLUIDA
        );

        repository.save(denuncia);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void marcarErro(Long denunciaId) {

        repository.findById(denunciaId).ifPresent(denuncia -> {
            denuncia.setStatusAnaliseIA(StatusAnaliseIA.ERRO);
            repository.save(denuncia);
        });
    }
}