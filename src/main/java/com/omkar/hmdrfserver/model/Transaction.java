package com.omkar.hmdrfserver.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "transaction")
@Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String sender;


    private String recipient;


    private String note;


    private double amount;


    private Instant txnDate;

    public Transaction() {
    }

    public Transaction( String sender,  String recipient,  String note,  double amount,  Instant txnDate) {
        this.sender = sender;
        this.recipient = recipient;
        this.note = note;
        this.amount = amount;
        this.txnDate = txnDate;
    }
}
