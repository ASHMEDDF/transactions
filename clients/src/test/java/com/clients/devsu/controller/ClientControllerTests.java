package com.clients.devsu.controller;

import com.clients.devsu.entities.Client;
import com.clients.devsu.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


class ClientControllerTests {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void testGetAllClientes() throws Exception {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("John Doe");
        client1.setClientId(1L);

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Jane Doe");
        client2.setClientId(2L);

        when(clientService.findAll()).thenReturn(Arrays.asList(client1, client2));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[1].name", is("Jane Doe")));

        verify(clientService, times(1)).findAll();
    }

    @Test
    void testGetClientById() throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setName("John Doe");
        client.setClientId(1L);

        when(clientService.findById(anyLong())).thenReturn(Optional.of(client));

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")));

        verify(clientService, times(1)).findById(1L);
    }

    @Test
    void testGetClientByIdNotFound() throws Exception {
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/clientes/111"))
                .andExpect(status().isNotFound());

        verify(clientService, times(1)).findById(111L);
    }

    @Test
    void testCreateClient() throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setName("John Doe");
        client.setClientId(1L);

        when(clientService.save(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"clientId\": 1, \"password\": \"password\", \"estado\": true, \"gender\": \"Male\", \"age\": 30, \"identification\": \"123456789\", \"address\": \"123 Main St\", \"phone\": 5551234}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("John Doe")));

        verify(clientService, times(1)).save(any(Client.class));
    }

    @Test
    void testUpdateClient() throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setName("John Doe");
        client.setClientId(1L);

        when(clientService.findById(anyLong())).thenReturn(Optional.of(client));
        when(clientService.save(any(Client.class))).thenReturn(client);

        mockMvc.perform(put("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Smith\", \"clientId\": 1, \"password\": \"newpassword\", \"estado\": true, \"gender\": \"Male\", \"age\": 31, \"identification\": \"123456789\", \"address\": \"123 Main St\", \"phone\": 5551234}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Smith")));

        verify(clientService, times(1)).findById(1L);
        verify(clientService, times(1)).save(any(Client.class));
    }

    @Test
    void testPatchClient() throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setName("John Doe");
        client.setClientId(1L);

        when(clientService.findById(anyLong())).thenReturn(Optional.of(client));
        when(clientService.save(any(Client.class))).thenReturn(client);

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "John Smith");

        mockMvc.perform(patch("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Smith\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Smith")));

        verify(clientService, times(1)).findById(1L);
        verify(clientService, times(1)).save(any(Client.class));
    }

    @Test
    void testDeleteClient() throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setName("John Doe");
        client.setClientId(1L);

        when(clientService.findById(anyLong())).thenReturn(Optional.of(client));
        doNothing().when(clientService).deleteById(anyLong());

        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());

        verify(clientService, times(1)).findById(1L);
        verify(clientService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteClientNotFound() throws Exception {
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNotFound());

        verify(clientService, times(1)).findById(1L);
        verify(clientService, times(0)).deleteById(1L);
    }
}