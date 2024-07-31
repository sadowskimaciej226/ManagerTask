package com.example.managertask.client;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientDtoMapper clientDtoMapper;


    public ClientService(ClientRepository clientRepository, ClientDtoMapper clientDtoMapper) {
        this.clientRepository = clientRepository;
        this.clientDtoMapper = clientDtoMapper;
    }
    public Optional<ClientDto> getUserById(Long id) {
        return clientRepository.findById(id)
                .map(clientDtoMapper::map);
    }
}
