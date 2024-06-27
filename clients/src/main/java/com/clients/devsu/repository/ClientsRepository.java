package com.clients.devsu.repository;

import com.clients.devsu.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Client, Long> {


}