package com.transactions.devsu.repository;

import com.transactions.devsu.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.clientId = :clientId")
    List<Account> findByClientId(@Param("clientId") Long clientId);

}
