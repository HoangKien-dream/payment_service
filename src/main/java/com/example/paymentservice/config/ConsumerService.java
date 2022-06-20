package com.example.paymentservice.config;

import com.example.paymentservice.entity.Wallet;
import com.example.paymentservice.enums.Status;
import com.example.paymentservice.repository.RepositoryWallet;
import jdk.net.SocketFlow;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    @Autowired
    RepositoryWallet repositoryWallet;
    @Autowired
    RabbitTemplate rabbitTemplate;
    public void getMessage(OrderEvent orderEvent){
        Wallet wallet = repositoryWallet.findById(orderEvent.getUserId()).orElse(null);
        if (wallet.getAmount() <= orderEvent.getTotalPrice()){
            orderEvent.setStatusPayment(Status.PaymentStatus.NOT_ENOUGH_BALANCE.name());
            rabbitTemplate.convertAndSend(MessageConfig.DIRECT_EXCHANGE,MessageConfig.DIRECT_ROUTING_KEY_ORDER,orderEvent);
        }
        else {
            int amount = wallet.getAmount() - orderEvent.getTotalPrice();
            wallet.setAmount(amount);
            orderEvent.setStatusPayment(Status.PaymentStatus.DONE.name());
            rabbitTemplate.convertAndSend(MessageConfig.DIRECT_EXCHANGE,MessageConfig.DIRECT_ROUTING_KEY_ORDER,orderEvent);
            repositoryWallet.save(wallet);
        }
    }

    public void getMessageReturn(OrderEvent orderEvent){

    }
}
