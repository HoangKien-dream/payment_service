package com.example.paymentservice.config;


import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEvent {
    private int productId;
    private int quantity;
    private int unitPrice;
}
