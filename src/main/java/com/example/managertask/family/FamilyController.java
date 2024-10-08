package com.example.managertask.family;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
 class FamilyController {
    private final FamilyService familyService;

    @GetMapping("/family/{id}")
    ResponseEntity<FamilyDto> getFamilyById(@PathVariable Long id){
        return familyService.getFamilyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/client/{id}/family")
    ResponseEntity<?> saveFamily(@PathVariable Long id, @RequestBody FamilyDto familyDto){
        FamilyDto responseFamily = familyService.saveFamily(id,familyDto);
        URI savedFamily = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseFamily.getId())
                .toUri();
        return ResponseEntity.created(savedFamily).body(responseFamily);

    }
}
