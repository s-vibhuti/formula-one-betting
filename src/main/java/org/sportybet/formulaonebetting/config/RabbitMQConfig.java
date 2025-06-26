package org.sportybet.formulaonebetting.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@Getter
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.eventOutcome}")
    private String queueName;

    @Value("${rabbitmq.exchange.eventOutcome}")
    private String exchangeName;

    @Value("${rabbitmq.routingKey.eventOutcome}")
    private String routingKey;


    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}
