package com.example.managertask.client;

import org.springframework.stereotype.Service;

@Service
 class ClientDtoMapper {
    ClientDto map(Client client){
        ClientDto dto = new ClientDto();
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
}
