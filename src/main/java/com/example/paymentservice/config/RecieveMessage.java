package com.example.paymentservice.config;

import com.example.paymentservice.enums.Status;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.paymentservice.config.MessageConfig.QUEUE_PAY;

@Component
public class RecieveMessage {
    @Autowired
    ConsumerService consumerService;
    @RabbitListener(queues = {QUEUE_PAY})
    public void getMessage(OrderEvent orderEvent){
        orderEvent.setQueueName("QUEUE_PAY");
        if (orderEvent.getStatusPayment().equals("PENDING")){
            consumerService.getMessage(orderEvent);
        }
        if (orderEvent.getStatusOrder().equals(Status.OrderStatus.CANCEL.name())){
            consumerService.getMessageReturn(orderEvent);
        }
    }
}
