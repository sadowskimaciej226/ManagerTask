package com.example.managertask.family;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final FamilyDtoMapper familyDtoMapper;

    public FamilyService(FamilyRepository familyRepository, FamilyDtoMapper familyDtoMapper) {
        this.familyRepository = familyRepository;
        this.familyDtoMapper = familyDtoMapper;
    }


    Optional<FamilyDto> getFamilyById(Long id){
        return familyRepository.findById(id)
                .map(familyDtoMapper::map);
    }
}
