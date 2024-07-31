package com.example.managertask.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
 class ClientController {

 private final ClientService clientService;

    ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("user/{id}")
 ResponseEntity<ClientDto> getUserById(@PathVariable Long id){
     return clientService.getUserById(id)
             .map(ResponseEntity::ok)
             .orElse(ResponseEntity.notFound().build());
    }


}
