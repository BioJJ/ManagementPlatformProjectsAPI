package io.github.biojj.modules.client.model;

import io.github.biojj.modules.client.model.Client;
import io.github.biojj.modules.client.repository.ClientRepository;
import io.github.biojj.modules.client.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClientTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void testValidClient() {
        Client client = Client.builder()
                .name("John Doe")
                .email("john@example.com")
                .build();

        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidClient() {
        Client client = Client.builder()
                .name("")
                .email("invalidemail")
                .build();

        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertEquals(2, violations.size());
    }

    @Test
    public void testSaveClient() {
        Client client = Client.builder()
                .name("Jane Doe")
                .email("jane@example.com")
                .build();

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientService.save(client);

        assertEquals(client, savedClient);
    }
}
