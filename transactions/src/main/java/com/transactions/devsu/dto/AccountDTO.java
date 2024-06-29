package com.transactions.devsu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public
class AccountDTO {
    private Long id;
    private Integer accountNumber;
    private String accountType;
    private Double initialBalance;
    private Double currentBalance;
    private Boolean status;
    private List<MovementDTO> movements;
}
