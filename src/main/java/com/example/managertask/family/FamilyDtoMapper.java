package com.example.managertask.family;

import com.example.managertask.client.Client;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
 class FamilyDtoMapper {
    FamilyDto map(Family family){
        FamilyDto dto = new FamilyDto();
        dto.setId(family.getId());
        dto.setFamilyName(family.getFamilyName());
        dto.setUsernames(family.getClients()
                .stream()
                .map(Client::getFirstName)
                .collect(Collectors.toList()));
        dto.setUsersId(family.getClients()
                .stream()
                .map(Client::getId)
                .collect(Collectors.toSet()));
        return dto;
    }
}
