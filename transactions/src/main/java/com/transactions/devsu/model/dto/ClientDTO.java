package com.transactions.devsu.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private Integer phone;
    private String password;
    private Boolean state;
}
