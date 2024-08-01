package com.example.managertask.family;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
 class FamilyController {
    private final FamilyService familyService;

    FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @GetMapping("/family/{id}")
    ResponseEntity<FamilyDto> getFamilyById(@PathVariable Long id){
        return familyService.getFamilyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
