package com.omkar.hmdrfserver.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "balance")
@Data
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private double balance;

    public Balance() {}
    public Balance(String name, double balance) {
        this.name=name;
        this.balance=balance;
    }
}
