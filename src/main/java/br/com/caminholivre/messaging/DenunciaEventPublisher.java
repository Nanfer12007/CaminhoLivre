package br.com.caminholivre.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import br.com.caminholivre.config.RabbitMQConfig;

@Component
public class DenunciaEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public DenunciaEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    public void publicar(DenunciaCriadaEvent event) {

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            RabbitMQConfig.ROUTING_KEY,
            event.denunciaId().toString()
        );
    }
}