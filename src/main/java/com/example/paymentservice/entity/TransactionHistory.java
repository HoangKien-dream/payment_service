package com.example.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sender;
    private String receiver;
    private int price;
    private String note;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public TransactionHistory(String sender, String receiver, int price, String note) {
        this.sender = sender;
        this.receiver = receiver;
        this.price = price;
        this.note = note;
    }
}
