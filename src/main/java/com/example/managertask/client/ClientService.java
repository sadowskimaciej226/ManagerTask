package com.example.managertask.client;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientDtoMapper clientDtoMapper;

     Optional<ClientDto> getUserById(Long id) {
        return clientRepository.findById(id)
                .map(clientDtoMapper::map);
    }
    public Optional<Client> getUserByEmail(String email){
       return clientRepository.findClientByEmail(email);
    }

    Optional<ClientDto> getUserForCurrentUser(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent() && client.get().getEmail().equals(currentUserName)) {
            return client.map(clientDtoMapper::map);
        } else return Optional.empty();
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    ClientDto updateUser(ClientDto clientDto){
        Client client = clientDtoMapper.map(clientDto);
        Client save = clientRepository.save(client);
        return clientDtoMapper.map(save);
    }

}
