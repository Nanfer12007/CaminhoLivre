package br.com.caminholivre.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE =
            "caminholivre.exchange";

    public static final String QUEUE =
            "denuncia.analise.queue";

    public static final String ROUTING_KEY =
            "denuncia.criada";

    public static final String DLX =
            "caminholivre.dlx";

    public static final String DLQ =
            "denuncia.analise.dlq";

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX);
    }

    @Bean
    Queue denunciaQueue() {
        return QueueBuilder.durable(QUEUE)
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument(
                    "x-dead-letter-routing-key",
                    DLQ
                )
                .build();
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    Binding denunciaBinding() {
        return BindingBuilder
                .bind(denunciaQueue())
                .to(exchange())
                .with(ROUTING_KEY);
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DLQ);
    }
}