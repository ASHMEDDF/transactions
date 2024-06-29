package com.clients.devsu.repository;

import com.clients.devsu.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientsRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientId(Long clientId);

}