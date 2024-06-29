package com.transactions.devsu.controller;

import com.transactions.devsu.model.dto.ClientDTO;
import com.transactions.devsu.model.entities.Account;
import com.transactions.devsu.exceptions.ResourceNotFoundException;
import com.transactions.devsu.services.AccountService;
import com.transactions.devsu.services.CustomerClient;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cuentas")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerClient customerClient;

    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account not found for this id = ";

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE + id));
        return ResponseEntity.ok().body(account);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody Account account) throws BadRequestException {

        ClientDTO clientGot = customerClient.findById(account.getClientId());

        if (clientGot != null && Boolean.TRUE.equals(clientGot.getState())) {
            return accountService.save(account);
        } else {

            if (clientGot == null) {
                throw new BadRequestException("Client not found for this id = " + account.getClientId());
            } else {
                throw new BadRequestException("Client inactive for this id = " + account.getClientId());
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
        Account account = accountService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE + id));

        account.setAccountNumber(accountDetails.getAccountNumber());
        account.setAccountType(accountDetails.getAccountType());
        account.setInitialBalance(accountDetails.getInitialBalance());
        account.setStatus(accountDetails.getStatus());

        final Account updatedAccount = accountService.save(account);
        return ResponseEntity.ok(updatedAccount);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Account> patchAccount(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Account account = accountService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "accountNumber" -> account.setAccountNumber((Integer) value);
                case "accountType" -> account.setAccountType((String) value);
                case "initialBalance" -> account.setInitialBalance(((Number) value).doubleValue());
                case "status" -> account.setStatus((Boolean) value);
                default -> {
                    try {
                        throw new BadRequestException("Invalid field: " + key);
                    } catch (BadRequestException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        final Account updatedAccount = accountService.save(account);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        Account account = accountService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE + id));

        if(account != null){
            accountService.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
}