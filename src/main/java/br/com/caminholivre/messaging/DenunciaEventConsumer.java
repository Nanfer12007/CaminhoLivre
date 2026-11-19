package br.com.caminholivre.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.caminholivre.ai.AnaliseAcessibilidadeIA;
import br.com.caminholivre.ai.AnaliseAcessibilidadeService;
import br.com.caminholivre.config.RabbitMQConfig;
import br.com.caminholivre.entity.Denuncia;
import br.com.caminholivre.repository.DenunciaRepository;
import br.com.caminholivre.service.DenunciaAnalisePersistenceService;

@Component
public class DenunciaEventConsumer {

    private final DenunciaRepository repository;
    private final AnaliseAcessibilidadeService aiService;
    private final DenunciaAnalisePersistenceService persistenceService;

    public DenunciaEventConsumer(
            DenunciaRepository repository,
            AnaliseAcessibilidadeService aiService,
            DenunciaAnalisePersistenceService persistenceService) {

        this.repository = repository;
        this.aiService = aiService;
        this.persistenceService = persistenceService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumir(String mensagem) {

        Long denunciaId = Long.valueOf(mensagem);

        try {
            Denuncia denuncia = repository.findById(denunciaId)
                .orElseThrow(() ->
                    new IllegalStateException(
                        "Denúncia não encontrada: " + denunciaId
                    )
                );

            AnaliseAcessibilidadeIA resultado =
                aiService.analisar(denuncia);

            persistenceService.salvarResultado(
                denunciaId,
                resultado
            );

            System.out.println(
                "Análise concluída para denúncia " + denunciaId
            );

        } catch (Exception e) {

            persistenceService.marcarErro(denunciaId);

            System.err.println(
                "Erro ao analisar denúncia " + denunciaId
                    + ": " + e.getMessage()
            );

            throw new IllegalStateException(
                "Falha no processamento da denúncia",
                e
            );
        }
    }
}