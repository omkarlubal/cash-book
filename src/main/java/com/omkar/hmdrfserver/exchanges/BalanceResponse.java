package com.omkar.hmdrfserver.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceResponse {
    private boolean success;
    private String name;
    private double balance;
}
