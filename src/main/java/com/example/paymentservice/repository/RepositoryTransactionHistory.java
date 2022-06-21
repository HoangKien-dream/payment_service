package com.example.paymentservice.repository;

import com.example.paymentservice.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryTransactionHistory extends JpaRepository<TransactionHistory,String> {
}
