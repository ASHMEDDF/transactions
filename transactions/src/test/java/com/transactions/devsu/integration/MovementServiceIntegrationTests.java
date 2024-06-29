package com.transactions.devsu.integration;

import com.transactions.devsu.dto.ClientDTO;
import com.transactions.devsu.entities.Account;
import com.transactions.devsu.entities.Movement;
import com.transactions.devsu.exceptions.BadRequestException;
import com.transactions.devsu.exceptions.InsufficientBalanceException;
import com.transactions.devsu.exceptions.ResourceNotFoundException;
import com.transactions.devsu.repository.MovementRepository;
import com.transactions.devsu.services.AccountService;
import com.transactions.devsu.services.CustomerClient;
import com.transactions.devsu.services.MovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MovementServiceIntegrationTests {

    @Autowired
    private MovementService movementService;

    @Autowired
    private AccountService accountService;

    @MockBean
    private CustomerClient customerClient;

    @Autowired
    private MovementRepository movementRepository;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setAccountNumber(123456);
        testAccount.setAccountType("Savings");
        testAccount.setInitialBalance(1000.0);
        testAccount.setStatus(true);
        testAccount.setClientId(1L);
        accountService.save(testAccount);
    }

    @Test
    void testSaveMovementDebit() {
        Movement movement = new Movement();
        movement.setAccount(testAccount);
        movement.setAmount(500.0);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setState(true);
        when(customerClient.findById(anyLong())).thenReturn(clientDTO);

        Movement savedMovement = movementService.save(movement);

        assertNotNull(savedMovement.getId());
        assertEquals("debito", savedMovement.getType());
        assertEquals(1500.0, savedMovement.getBalance());
    }

    @Test
    void testSaveMovementWithdrawal() {
        Movement movement = new Movement();
        movement.setAccount(testAccount);
        movement.setAmount(-500.0);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setState(true);
        when(customerClient.findById(anyLong())).thenReturn(clientDTO);

        Movement savedMovement = movementService.save(movement);

        assertNotNull(savedMovement.getId());
        assertEquals("retiro", savedMovement.getType());
        assertEquals(500.0, savedMovement.getBalance());
    }

    @Test
    void testSaveMovementInsufficientBalance() {
        Movement movement = new Movement();
        movement.setAccount(testAccount);
        movement.setAmount(-1500.0);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setState(true);
        when(customerClient.findById(anyLong())).thenReturn(clientDTO);

        assertThrows(InsufficientBalanceException.class, () -> movementService.save(movement));
    }

    @Test
    void testSaveMovementAccountNotFound() {
        Movement movement = new Movement();
        Account nonExistentAccount = new Account();
        nonExistentAccount.setId(999L);
        movement.setAccount(nonExistentAccount);
        movement.setAmount(500.0);

        assertThrows(ResourceNotFoundException.class, () -> movementService.save(movement));
    }

    @Test
    void testSaveMovementClientNotActive() {
        Movement movement = new Movement();
        movement.setAccount(testAccount);
        movement.setAmount(500.0);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setState(false);
        when(customerClient.findById(anyLong())).thenReturn(clientDTO);

        assertThrows(BadRequestException.class, () -> movementService.save(movement));
    }

    @Test
    void testSaveMovementAccountNotActive() {
        testAccount.setStatus(false);
        accountService.save(testAccount);

        Movement movement = new Movement();
        movement.setAccount(testAccount);
        movement.setAmount(500.0);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setState(true);
        when(customerClient.findById(anyLong())).thenReturn(clientDTO);

        assertThrows(BadRequestException.class, () -> movementService.save(movement));
    }

    @Test
    void testFindById() {
        Movement movement = new Movement();
        movement.setAccount(testAccount);
        movement.setAmount(500.0);
        movement.setType("debito");
        movement.setBalance(1500.0);
        movement.setDate(LocalDateTime.now());

        Movement savedMovement = movementRepository.save(movement);
        Optional<Movement> foundMovement = movementService.findById(savedMovement.getId());

        assertTrue(foundMovement.isPresent());
        assertEquals(savedMovement.getId(), foundMovement.get().getId());
    }

    @Test
    void testFindAll() {
        Movement movement1 = new Movement();
        movement1.setAccount(testAccount);
        movement1.setAmount(500.0);
        movement1.setType("debito");
        movement1.setBalance(1500.0);
        movement1.setDate(LocalDateTime.now());

        Movement movement2 = new Movement();
        movement2.setAccount(testAccount);
        movement2.setAmount(-200.0);
        movement2.setType("retiro");
        movement2.setBalance(1300.0);
        movement2.setDate(LocalDateTime.now());

        movementRepository.save(movement1);
        movementRepository.save(movement2);

        List<Movement> movements = movementService.findAll();

        assertEquals(2, movements.size());
    }
}
