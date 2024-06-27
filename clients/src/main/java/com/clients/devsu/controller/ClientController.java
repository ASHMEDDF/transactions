package com.clients.devsu.controller;

import com.clients.devsu.entities.Client;
import com.clients.devsu.exception.ResourceNotFoundException;
import com.clients.devsu.service.ClientService;
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
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    private static final String CLIENT_NOT_FOUND_MESSAGE = "Client not found for this id = ";

    @GetMapping
    public List<Client> getAllClientes() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> clientOptional = clientService.findById(id);

        return clientOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client createClient(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Client client = clientService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT_NOT_FOUND_MESSAGE + id));

        client.setClientId(clientDetails.getClientId());
        client.setPassword(clientDetails.getPassword());
        client.setEstado(clientDetails.getEstado());
        client.setName(clientDetails.getName());
        client.setGender(clientDetails.getGender());
        client.setAge(clientDetails.getAge());
        client.setIdentification(clientDetails.getIdentification());
        client.setAddress(clientDetails.getAddress());
        client.setPhone(clientDetails.getPhone());

        final Client updatedClient = clientService.save(client);
        return ResponseEntity.ok(updatedClient);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Client> patchClient(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Client client = clientService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT_NOT_FOUND_MESSAGE + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "clientId" -> client.setClientId(((Number) value).longValue());
                case "password" -> client.setPassword((String) value);
                case "estado" -> client.setEstado((Boolean) value);
                case "name" -> client.setName((String) value);
                case "gender" -> client.setGender((String) value);
                case "age" -> client.setAge((Integer) value);
                case "identification" -> client.setIdentification((String) value);
                case "address" -> client.setAddress((String) value);
                case "phone" -> client.setPhone((Integer) value);
                default -> {
                    try {
                        throw new BadRequestException("Invalid field: " + key);
                    } catch (BadRequestException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        final Client updatedClient = clientService.save(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        Optional<Client> clientOptional = clientService.findById(id);
        if (clientOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}