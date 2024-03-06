package com.zack.api.infra.queue.consumers;

import com.zack.api.dtos.EmailSendDto;
import com.zack.api.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MailConsumer {

    @Autowired
    EmailService emailService;
    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenMail(@Payload EmailSendDto payload){
        emailService.sendMail(payload.content(),payload.subject(),payload.to());
    }
}
