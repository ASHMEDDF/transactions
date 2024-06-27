package com.clients.devsu.service;

import com.clients.devsu.entities.Client;
import com.clients.devsu.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Client> findAll() {
        return clientsRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientsRepository.findById(id);
    }

    public Client save(Client client) {
        return clientsRepository.save(client);
    }

    public void deleteById(Long id) {
        clientsRepository.deleteById(id);
    }
}