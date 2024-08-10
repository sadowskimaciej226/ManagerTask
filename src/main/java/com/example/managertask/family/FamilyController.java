package com.example.managertask.family;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    @PostMapping("/family")
    ResponseEntity<?> saveFamily(@RequestBody FamilyDto familyDto){
        FamilyDto responseFamily = familyService.saveFamily(familyDto);
        URI savedFamily = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseFamily.getId())
                .toUri();
        return ResponseEntity.created(savedFamily).body(responseFamily);

    }
}
