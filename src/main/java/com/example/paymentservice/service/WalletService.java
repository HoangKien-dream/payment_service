package com.example.paymentservice.service;

import com.example.paymentservice.entity.TransactionHistory;
import com.example.paymentservice.entity.Wallet;
import com.example.paymentservice.repository.RepositoryTransactionHistory;
import com.example.paymentservice.repository.RepositoryWallet;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {
    @Autowired
    RepositoryTransactionHistory repositoryTransactionHistory;
    @Autowired
    RepositoryWallet repositoryWallet;
    public List<TransactionHistory> findAll(){
        return repositoryTransactionHistory.findAll();
    }
    public Wallet getWallet(int id){
        Wallet wallet = repositoryWallet.findById(id).orElse(null);
        if (wallet != null){
            return wallet;
        }
        return null;
    }
}
