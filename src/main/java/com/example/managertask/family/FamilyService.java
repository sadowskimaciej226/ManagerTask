package com.example.managertask.family;

import com.example.managertask.client.Client;
import com.example.managertask.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final FamilyDtoMapper familyDtoMapper;
    private final ClientRepository clientRepository;



    Optional<FamilyDto> getFamilyById(Long id){
        return familyRepository.findById(id)
                .map(familyDtoMapper::map);
    }
    FamilyDto saveFamily(Long id,FamilyDto familyDto) {
        Client client = clientRepository.findById(id).orElseThrow();
        if (client.getFamily() == null) {
            Family toSave = familyDtoMapper.map(familyDto);
            toSave.setClients(List.of(client));
            Family saved = familyRepository.save(toSave);
            client.setFamily(saved);
            clientRepository.save(client);
            return familyDtoMapper.map(saved);
        }else {
            throw new IllegalArgumentException();
        }

    }
}
