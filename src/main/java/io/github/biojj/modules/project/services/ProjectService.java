package io.github.biojj.modules.client.services;


import io.github.biojj.exception.EmailExistingException;
import io.github.biojj.modules.client.model.Client;
import io.github.biojj.modules.client.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClientService {

    private final ProjectRepository clientRepository;

    public ClientService(ProjectRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client client) {
        boolean exists = clientRepository.existsByEmail(client.getEmail());

        if (exists) {
            throw new EmailExistingException(client.getEmail());
        }

        return clientRepository.save(client);
    }

    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client findById(Long id) {
        return clientRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client não encontrado"));
    }

    public void delete(Long id) {
        clientRepository
                .findById(id)
                .map(client -> {
                    clientRepository.delete(client);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client não encontrado"));
    }

    public void update(Long id,
                       Client clientDTO) {

        clientRepository
                .findById(id)
                .map(client -> {
                    client.setName(clientDTO.getName());
                    client.setEmail(clientDTO.getEmail());

                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client não encontrado"));
    }

}
