package com.example.paymentservice.controller;

import com.example.paymentservice.entity.TransactionHistory;
import com.example.paymentservice.entity.Wallet;
import com.example.paymentservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/wallet")
public class WalletController {
    @Autowired
    WalletService walletService;
    @RequestMapping(method = RequestMethod.GET)
    public List<TransactionHistory> findAll(){
        return walletService.findAll();
    }
    @RequestMapping(path = "{id}",method = RequestMethod.GET)
    public Wallet getWallet(@PathVariable int id){
        return walletService.getWallet(id);
    }
}
