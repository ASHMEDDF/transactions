package com.transactions.devsu.services;

import com.transactions.devsu.dto.AccountDTO;
import com.transactions.devsu.dto.ClientDTO;
import com.transactions.devsu.dto.MovementDTO;
import com.transactions.devsu.dto.ReportDTO;
import com.transactions.devsu.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private MovementService movementService;

    public ReportDTO generateReport(Long clientId, LocalDateTime startDate, LocalDateTime endDate) {
        List<AccountDTO> accounts;

        if (clientId != null) {
            ClientDTO client = customerClient.findById(clientId);

            accounts = accountService.findByClientId(clientId).stream()
                    .map(account -> mapAccountWithMovements(account, startDate, endDate))
                    .toList();

            return new ReportDTO(client, accounts);

        } else {
            // If clientId is null, retrieve all accounts and their movements
            accounts = accountService.findAll().stream()
                    .map(account -> mapAccountWithMovements(account, startDate, endDate))
                    .toList();

            return new ReportDTO(null, accounts);
        }
    }

    private AccountDTO mapAccountWithMovements(Account account, LocalDateTime startDate, LocalDateTime endDate) {
        List<MovementDTO> movements = movementService.findByAccountIdAndDateRange(account.getId(), startDate, endDate).stream()
                .map(movement -> new MovementDTO(
                        movement.getId(),
                        movement.getDate().toString(),
                        movement.getType(),
                        movement.getAmount(),
                        movement.getBalance()))
                .toList();

        return new AccountDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getInitialBalance(),
                account.getInitialBalance(),
                account.getStatus(),
                movements);
    }
}