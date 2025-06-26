package org.sportybet.formulaonebetting.publisher;

import org.sportybet.formulaonebetting.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FormulaOneEventOutcomPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.eventOutcome}")
    private String exchangeName;

    @Value("${rabbitmq.routingKey.eventOutcome}")
    private String routingKey;


    public void publish(String messageJson) {
        rabbitTemplate.convertAndSend(
                exchangeName,
                routingKey,
                messageJson
        );
    }

}
