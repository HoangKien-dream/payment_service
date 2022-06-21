package com.example.paymentservice.config;

import com.example.paymentservice.entity.TransactionHistory;
import com.example.paymentservice.entity.Wallet;
import com.example.paymentservice.enums.Status;
import com.example.paymentservice.repository.RepositoryTransactionHistory;
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
    RepositoryTransactionHistory transactionHistory;
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void getMessage(OrderEvent orderEvent) {

        Wallet wallet = repositoryWallet.findById(orderEvent.getUserId()).orElse(null);
        if (wallet.getAmount() <= orderEvent.getTotalPrice()) {
            orderEvent.setStatusPayment(Status.PaymentStatus.NOT_ENOUGH_BALANCE.name());
            rabbitTemplate.convertAndSend(MessageConfig.DIRECT_EXCHANGE, MessageConfig.DIRECT_ROUTING_KEY_ORDER, orderEvent);
        } else {
            TransactionHistory history = new TransactionHistory();
            int amount = wallet.getAmount() - orderEvent.getTotalPrice();
            wallet.setAmount(amount);
            orderEvent.setStatusPayment(Status.PaymentStatus.DONE.name());
            history.setSender(wallet.getUsername());
            history.setReceiver("DreamVn");
            history.setPrice(orderEvent.getTotalPrice());
            history.setNote(wallet.getUsername() + " chuyển tiền cho Dreamvn với mã đơn hàng" + orderEvent.getOrderId());
            rabbitTemplate.convertAndSend(MessageConfig.DIRECT_EXCHANGE, MessageConfig.DIRECT_ROUTING_KEY_ORDER, orderEvent);
            repositoryWallet.save(wallet);
            transactionHistory.save(history);
        }
    }

    public void getMessageReturn(OrderEvent orderEvent) {
        TransactionHistory history = new TransactionHistory();
        Wallet wallet = repositoryWallet.findById(orderEvent.getUserId()).orElse(null);
        int amount = wallet.getAmount() + orderEvent.getTotalPrice();
        wallet.setAmount(amount);
        orderEvent.setStatusPayment(Status.PaymentStatus.REFUNDED.name());
        rabbitTemplate.convertAndSend(MessageConfig.DIRECT_EXCHANGE, MessageConfig.DIRECT_ROUTING_KEY_ORDER, orderEvent);
        history.setSender("DreamVn");
        history.setReceiver(wallet.getUsername());
        history.setPrice(orderEvent.getTotalPrice());
        history.setNote("Dream chuyển tiền cho " +wallet.getUsername()+ "với mã đơn hàng" +orderEvent.getOrderId());
        repositoryWallet.save(wallet);
        transactionHistory.save(history);
    }
}
