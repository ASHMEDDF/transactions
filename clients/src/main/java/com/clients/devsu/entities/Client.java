package com.clients.devsu.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Client extends Person{

    @Column(unique = true)
    private Long clientId;

    @Column(nullable = false)
    private String password;

    private Boolean state;

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return clientId != null && Objects.equals(clientId, client.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId != null ? clientId.hashCode() : 0);
    }
}
