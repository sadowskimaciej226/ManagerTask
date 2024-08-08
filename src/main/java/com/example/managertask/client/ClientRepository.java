package com.example.managertask.client;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface ClientRepository extends CrudRepository<Client, Long> {
  Optional<Client> findClientByEmail(String email);
}
