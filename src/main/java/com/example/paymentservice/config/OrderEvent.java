package com.example.paymentservice.config;

import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    public int orderId;
    public int userId;
    public int totalPrice;
    public Set<OrderDetailEvent> orderDetailEvents;
    public String statusInventory;
    public String statusOrder;
    public String statusPayment;
    public String queueName;
}
