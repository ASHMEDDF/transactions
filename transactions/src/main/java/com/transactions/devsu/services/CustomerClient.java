package com.transactions.devsu.services;

import com.transactions.devsu.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ComponentScan
@FeignClient(name = "customer-service", url = "http://localhost:8080/clientes")
public interface CustomerClient {

    @GetMapping("/{id}")
    ClientDTO findById(@PathVariable("id") Long id);
}
