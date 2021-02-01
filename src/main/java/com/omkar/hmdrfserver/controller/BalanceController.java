package com.omkar.hmdrfserver.controller;

import com.omkar.hmdrfserver.exchanges.ApiResponse;
import com.omkar.hmdrfserver.exchanges.NewBalance;
import com.omkar.hmdrfserver.model.Balance;
import com.omkar.hmdrfserver.security.JwtTokenUtil;
import com.omkar.hmdrfserver.service.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @Autowired
    BalanceService balanceService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/create")
    public ResponseEntity<?> createBalance(@RequestBody NewBalance newBalance) {
        String accountName = newBalance.getName();
        double balance = newBalance.getAmount();
        log.info("Call create new account, name: {} balance: {}", accountName, balance);
        if (!StringUtils.hasText(accountName) || balance < 0) {
            throw new BadCredentialsException("Check account details again!");
        }
        balanceService.createBalance(accountName, balance);
        return ResponseEntity.ok(new ApiResponse(true, "Created new balance with name: "+newBalance.getName()));
    }

    @GetMapping("/accounts/get")
    public ResponseEntity<?> getAllBalanceAccountsNames(@RequestHeader("Authorization") String token) {
        log.info("Call Get balance account names for user : {}",jwtTokenUtil.getUsernameFromToken(token.substring(7)));
        List<String> accounts = balanceService.getAllBalanceAccountNames();
        Map<String,Object> response = new HashMap<>();
        response.put("success",true);
        response.put("accounts",accounts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/accounts/getAll")
    public ResponseEntity<?> getAllBalanceAccounts(@RequestHeader("Authorization") String token) {
        log.info("Call Get balance account details for user : {}",jwtTokenUtil.getUsernameFromToken(token.substring(7)));
        List<Balance> accounts = balanceService.getAllBalanceAccountDetails();
        Map<String,Object> response = new HashMap<>();
        response.put("success",true);
        response.put("accounts",accounts);
        return ResponseEntity.ok(response);
    }
}
