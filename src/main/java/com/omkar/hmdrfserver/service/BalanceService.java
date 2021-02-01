package com.omkar.hmdrfserver.service;

import com.omkar.hmdrfserver.exception.BalanceNotFound;
import com.omkar.hmdrfserver.exception.InsufficientBalance;
import com.omkar.hmdrfserver.exception.UserAlreadyExists;
import com.omkar.hmdrfserver.exchanges.TransactionRequest;
import com.omkar.hmdrfserver.model.Balance;
import com.omkar.hmdrfserver.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BalanceService {

    @Autowired
    BalanceRepository balanceRepository;

    public void transferFunds(TransactionRequest transaction) {
        // transfer funds from sender to recip.
        String sender = transaction.getSender();
        String recipient = transaction.getRecipient();
        double amount = transaction.getAmount();

        Balance senderBalance = balanceRepository.findByName(sender)
                .orElseThrow(() -> new BalanceNotFound("Can't find balance for "+sender));
        Balance recipientBalance = balanceRepository.findByName(recipient)
                .orElseThrow(() -> new BalanceNotFound("Can't find balance for "+recipient));
        if (senderBalance.getBalance() - amount < 0) {
            throw new InsufficientBalance("Sender doesn't have enough Balance");
        }
        senderBalance.setBalance(senderBalance.getBalance() - amount);
        balanceRepository.save(senderBalance);
        recipientBalance.setBalance(recipientBalance.getBalance() + amount);
        balanceRepository.save(senderBalance);
    }

    public void createBalance(String newRecipient, double amt) {
        if (!balanceRepository.existsByName(newRecipient)) {
            balanceRepository.save(new Balance(newRecipient, amt));
        } else {
            throw new UserAlreadyExists("User already exists. Can't create!");
        }
    }

    public Balance getBalance(String name) {
        return balanceRepository.findByName(name).orElseThrow(
                () -> new BalanceNotFound("Can't find balance for "+name)
        );
    }

    public List<String> getAllBalanceAccountNames() {
        return balanceRepository.findAll().stream().map(Balance::getName).collect(Collectors.toList());
    }

    public List<Balance> getAllBalanceAccountDetails() {
        return balanceRepository.findAll();
    }
}
/*
    HM - > HMDRF
    HMDRF -> HM
    When
        HMDRF -> Other
        Other -> HDMRF

     in balance table, hmdrf will be already there
     and hm members should also be there
 */