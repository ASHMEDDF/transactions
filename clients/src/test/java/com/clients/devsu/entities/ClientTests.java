package com.clients.devsu.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientTests {

    @Test
    void testClientProperties() {
        Client client = new Client();
        client.setClientId(1L);
        client.setName("John Doe");
        client.setPassword("password");
        client.setState(true);
        client.setGender("Male");
        client.setAge(30);
        client.setIdentification("123456789");
        client.setAddress("123 Main St");
        client.setPhone(5551234);

        assertEquals(1L, client.getClientId());
        assertEquals("John Doe", client.getName());
        assertTrue(client.getState());
        assertEquals("Male", client.getGender());
        assertEquals(30, client.getAge());
        assertEquals("123456789", client.getIdentification());
        assertEquals("123 Main St", client.getAddress());
        assertEquals(5551234, client.getPhone());
    }

    @Test
    void testClientEquality() {
        Client client1 = new Client();
        client1.setClientId(1L);
        client1.setName("John Doe");

        Client client2 = new Client();
        client2.setClientId(1L);
        client2.setName("John Doe");

        assertEquals(client1, client2);

        client2.setClientId(2L);

        assertNotEquals(client1, client2);
    }

    @Test
    void testClientHashCode() {
        Client client1 = new Client();
        client1.setClientId(1L);
        client1.setName("John Doe");

        Client client2 = new Client();
        client2.setClientId(2L);
        client2.setName("John Doe");

        assertTrue(Math.abs(client1.hashCode() - client2.hashCode()) <= 1);

        client2.setName("Jane Does");

        assertNotEquals(client1.hashCode(), client2.hashCode());
    }
}
