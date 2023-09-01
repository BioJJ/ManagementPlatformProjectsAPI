package io.github.biojj.modules.client.controller;

import io.github.biojj.modules.client.model.Client;
import io.github.biojj.modules.client.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    private ClientController clientController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        clientController = new ClientController(clientService);
    }

    @Test
    public void testSaveClient() {
        Client client = new Client();
        client.setId(1L);

        when(clientService.save(any(Client.class))).thenReturn(client);

        Client response = clientController.save(client);

        assertNotNull(response);
        assertEquals(client, response);

        verify(clientService, times(1)).save(client);
    }

    @Test
    public void testFindAllClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());

        Page<Client> clientPage = new PageImpl<>(clients);

        when(clientService.findAll(any(Pageable.class))).thenReturn(clientPage);

        Page<Client> response = clientController.findAll(0, 10, "id");

        assertNotNull(response);
        assertEquals(clients.size(), response.getContent().size());

        verify(clientService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testFindClientById() {
        Client client = new Client();
        client.setId(1L);

        when(clientService.findById(anyLong())).thenReturn(client);

        Client response = clientController.findById(1L);

        assertNotNull(response);
        assertEquals(client, response);

        verify(clientService, times(1)).findById(1L);
    }

    @Test
    public void testFindNonexistentClientById() {
        when(clientService.findById(anyLong())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        assertThrows(ResponseStatusException.class, () -> clientController.findById(1L));

        verify(clientService, times(1)).findById(1L);
    }

    @Test
    public void testDeleteClient() {
        doNothing().when(clientService).delete(anyLong());

        assertDoesNotThrow(() -> clientController.deletar(1L));

        verify(clientService, times(1)).delete(1L);
    }

    @Test
    public void testUpdateClient() {
        Client updatedClient = new Client();
        updatedClient.setId(1L);

        doNothing().when(clientService).update(anyLong(), any(Client.class));

        assertDoesNotThrow(() -> clientController.atualizar(1L, updatedClient));

        verify(clientService, times(1)).update(1L, updatedClient);
    }
}
