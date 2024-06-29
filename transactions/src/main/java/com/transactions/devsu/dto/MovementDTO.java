package com.transactions.devsu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public
class MovementDTO {
    private Long id;
    private String date;
    private String type;
    private Double amount;
    private Double balance;
}
