package com.clients.devsu.integration;

import com.clients.devsu.entities.Client;
import com.clients.devsu.repository.ClientsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ClientControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientsRepository clientRepository;

    private Client client;

    @BeforeEach
    public void setup() {
        clientRepository.deleteAll();
        client = new Client();
        client.setClientId(1L);
        client.setName("John Doe");
        client.setPassword("password");
        client.setEstado(true);
        client.setGender("Male");
        client.setAge(30);
        client.setIdentification("123456789");
        client.setAddress("123 Main St");
        client.setPhone(5551234);
        client = clientRepository.save(client);
    }

    @Test
    void testGetAllClientes() throws Exception {
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(client.getName())));
    }

    @Test
    void testGetClientById() throws Exception {
        Long savedClientId = client.getId();

        mockMvc.perform(get("/clientes/" + savedClientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(client.getName())));
    }

    @Test
    void testGetClientByIdNotFound() throws Exception {
        mockMvc.perform(get("/clientes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateClient() throws Exception {
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Jane Doe\", \"password\": \"password\", \"estado\": true, \"gender\": \"Female\", \"age\": 25, \"identification\": \"987654321\", \"address\": \"456 Elm St\", \"phone\": 5555678}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Jane Doe")));
    }

    @Test
    void testUpdateClient() throws Exception {

        Long savedClientId = client.getId();

        mockMvc.perform(put("/clientes/" + savedClientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Smith\", \"password\": \"newpassword\", \"estado\": true, \"gender\": \"Male\", \"age\": 31, \"identification\": \"123456789\", \"address\": \"123 Main St\", \"phone\": 5555678}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Smith")));
    }

    @Test
    void testPatchClient() throws Exception {

        Long savedClientId = client.getId();

        mockMvc.perform(patch("/clientes/" + savedClientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Smith\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Smith")));
    }

    @Test
    void testDeleteClient() throws Exception {
        Long savedClientId = client.getId();

        mockMvc.perform(delete("/clientes/" + savedClientId))
                .andExpect(status().isNoContent());

        Optional<Client> deletedClient = clientRepository.findById(client.getClientId());
        assertTrue(deletedClient.isEmpty());
    }

    @Test
    void testDeleteClientNotFound() throws Exception {
        mockMvc.perform(delete("/clientes/999"))
                .andExpect(status().isNotFound());
    }
}