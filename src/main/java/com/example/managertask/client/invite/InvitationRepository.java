package com.example.managertask.client.invite;

import com.example.managertask.client.Client;
import com.example.managertask.family.Family;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InvitationRepository extends CrudRepository<Invitation,Long> {
    Optional<Invitation> findByFamilyAndInvitee(Family family, Client client);
}
