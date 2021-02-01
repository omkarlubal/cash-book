package com.omkar.hmdrfserver.repository;

import com.omkar.hmdrfserver.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByName(String name);
    Boolean existsByName(String name);
    List<Balance> findAll();
}
