package com.example.managertask.client;

import com.example.managertask.family.Family;
import com.example.managertask.family.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
 class ClientDtoMapper {
    private final FamilyRepository familyRepository;
    ClientDto map(Client client){
        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setFirstName(client.getFirstName());
        dto.setLastName(client.getLastName());
        dto.setEmail(client.getEmail());
        dto.setRole(client.getRole());
        dto.setPoints(client.getPoints());
        if(client.getFamily()!=null) {
            dto.setFamilyId(client.getFamily().getId());
            dto.setFamilyName(client.getFamily().getFamilyName());
        }
        return dto;
    }
    Client map(ClientDto dto){
        Client client = new Client();
        client.setId(dto.getId());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setPassword(dto.getPassword());
        client.setRole(dto.getRole());
        client.setPoints(dto.getPoints());
        if(dto.getFamilyId()!=null) {
            Family family = familyRepository.findById(dto.getFamilyId()).orElseThrow();
            client.setFamily(family);
        }
        return client;

    }


}
