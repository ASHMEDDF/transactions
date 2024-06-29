package com.transactions.devsu.services;

import com.transactions.devsu.model.dto.ClientDTO;
import com.transactions.devsu.model.entities.Account;
import com.transactions.devsu.model.entities.Movement;
import com.transactions.devsu.exceptions.BadRequestException;
import com.transactions.devsu.exceptions.InsufficientBalanceException;
import com.transactions.devsu.exceptions.ResourceNotFoundException;
import com.transactions.devsu.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovementService {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerClient customerClient;

    public List<Movement> findAll() {
        return movementRepository.findAll();
    }

    public Optional<Movement> findById(Long id) {
        return movementRepository.findById(id);
    }

    @Transactional
    public Movement save(Movement movement) {
        Account account = accountService.findById(movement.getAccount().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + movement.getAccount().getId()));

        String type = movement.getAmount() > 0 ? "debito" : "retiro";
        double newBalance = account.getInitialBalance() + movement.getAmount();

        ClientDTO client = customerClient.findById(account.getClientId());

        if (Boolean.TRUE.equals(account.getStatus() && client != null)
                && Boolean.TRUE.equals(client.getState())) {

            if (newBalance < 0) {
                throw new InsufficientBalanceException("Saldo insuficiente en la cuenta con Id: " + movement.getAccount().getId());
            }

            movement.setType(type);
            movement.setDate(LocalDateTime.now());
            movement.setBalance(newBalance);
            updateAccountBalance(account, newBalance); // Método privado para actualizar balance

            movement.setAccount(account);
            return movementRepository.save(movement);
        } else {

            throw new BadRequestException(getErrorMessage(account, Optional.ofNullable(client)));  // Método para construir mensaje de error
        }
    }

    private void updateAccountBalance(Account account, double newBalance) {
        account.setInitialBalance(newBalance);
        accountService.save(account);
    }

    private String getErrorMessage(Account account, Optional<ClientDTO> client) {
        if (Boolean.FALSE.equals(account.getStatus())) {
            return "La cuenta no está activa, su estado es: " + account.getStatus();
        } else
            return client.map(clientDTO -> "Cliente con Id " + clientDTO.getId() + " no está activo")
                    .orElseGet(() -> "Cliente no encontrado con id: " + account.getClientId());
    }

    public List<Movement> findByAccountIdAndDateRange(Long accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return movementRepository.findByAccountIdAndDateRange(accountId, startDate, endDate);
    }

}