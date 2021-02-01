package com.omkar.hmdrfserver.service;

import com.omkar.hmdrfserver.exchanges.TransactionRequest;
import com.omkar.hmdrfserver.model.Transaction;
import com.omkar.hmdrfserver.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction save (TransactionRequest tRequest) {
        Transaction transaction = new Transaction(
                tRequest.getSender(),
                tRequest.getRecipient(),
                tRequest.getNote(),
                tRequest.getAmount(),
                Instant.now()
        );
        return transactionRepository.save(transaction);
    }
}
