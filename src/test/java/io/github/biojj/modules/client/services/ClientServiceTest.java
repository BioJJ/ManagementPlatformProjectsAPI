package io.github.biojj.modules.client.services;

import io.github.biojj.exception.EmailExistingException;
import io.github.biojj.modules.client.model.Client;
import io.github.biojj.modules.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void testSaveNewClient() {
        Client client = new Client();
        client.setEmail("test@example.com");

        when(clientRepository.existsByEmail(client.getEmail())).thenReturn(false);
        when(clientRepository.save(client)).thenReturn(client);

        Client savedClient = clientService.save(client);

        assertNotNull(savedClient);
        assertEquals(client.getEmail(), savedClient.getEmail());

        verify(clientRepository, times(1)).existsByEmail(client.getEmail());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    public void testSaveExistingClient() {
        Client client = new Client();
        client.setEmail("test@example.com");

        when(clientRepository.existsByEmail(client.getEmail())).thenReturn(true);

        assertThrows(EmailExistingException.class, () -> clientService.save(client));

        verify(clientRepository, times(1)).existsByEmail(client.getEmail());
        verify(clientRepository, never()).save(client);
    }

    @Test
    public void testFindAllClients() {
        Page<Client> mockPage = mock(Page.class);
        when(clientRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<Client> clients = clientService.findAll(Pageable.unpaged());

        assertNotNull(clients);
        assertEquals(mockPage, clients);

        verify(clientRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testFindClientById() {
        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        Client foundClient = clientService.findById(client.getId());

        assertNotNull(foundClient);
        assertEquals(client.getId(), foundClient.getId());

        verify(clientRepository, times(1)).findById(client.getId());
    }

    @Test
    public void testFindClientByIdNotFound() {
        Long nonExistingId = 123L;
        when(clientRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> clientService.findById(nonExistingId));

        verify(clientRepository, times(1)).findById(nonExistingId);
    }

    @Test
    public void testDeleteClient() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        clientService.delete(clientId);

        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).delete(client);
    }

    @Test
    public void testDeleteClientNotFound() {
        Long nonExistingId = 123L;
        when(clientRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> clientService.delete(nonExistingId));

        verify(clientRepository, times(1)).findById(nonExistingId);
        verify(clientRepository, never()).delete(any());
    }

    @Test
    public void testUpdateClient() {
        Long clientId = 1L;
        Client existingClient = new Client();
        existingClient.setId(clientId);

        Client updatedClient = new Client();
        updatedClient.setName("Updated Name");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(existingClient)).thenReturn(updatedClient);

        clientService.update(clientId, updatedClient);

        assertEquals(updatedClient.getName(), existingClient.getName());

        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).save(existingClient);
    }

    @Test
    public void testUpdateClientNotFound() {
        Long nonExistingId = 123L;
        when(clientRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> clientService.update(nonExistingId, new Client()));

        verify(clientRepository, times(1)).findById(nonExistingId);
        verify(clientRepository, never()).save(any());
    }
}
