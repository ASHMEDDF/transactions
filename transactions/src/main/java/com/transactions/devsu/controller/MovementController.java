package com.transactions.devsu.controller;

import com.transactions.devsu.model.entities.Movement;
import com.transactions.devsu.exceptions.ResourceNotFoundException;
import com.transactions.devsu.services.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovementController {

    @Autowired
    private MovementService movementService;

    private static final String MOVEMENT_NOT_FOUND_MESSAGE = "Movement not found for this id = ";

    @GetMapping
    public List<Movement> getAllMovements() {
        return movementService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movement> getMovementById(@PathVariable Long id) {
        Movement movement = movementService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MOVEMENT_NOT_FOUND_MESSAGE + id));
        return ResponseEntity.ok().body(movement);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movement createMovement(@RequestBody Movement movement) {
        return movementService.save(movement);
    }

}
