package com.zack.api.infra.queue.producers;


import com.zack.api.dtos.EmailSendDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {
    @Value("${broker.queue.email.name}")
    private String mailQueue;
    final RabbitTemplate rabbitTemplate;
    public UserProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
    }

    public void publishMessageMail(EmailSendDto emailSendDto){

        rabbitTemplate.convertAndSend(mailQueue,emailSendDto);
    }
}
