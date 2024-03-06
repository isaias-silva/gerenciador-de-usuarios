package com.zack.api.infra.queue;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class RabbitConfig {

    @Value("${broker.queue.email.name}")
    private String mailQueue;

    @Bean
   public Queue queueMail(){

        return new Queue(mailQueue,true);
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter(){
        ObjectMapper objectMapper= new ObjectMapper();

        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
