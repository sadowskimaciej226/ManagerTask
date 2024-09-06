package com.example.managertask.client;

import com.example.managertask.client.invite.Invitation;
import com.example.managertask.client.invite.InvitationRepository;
import com.example.managertask.family.Family;
import com.example.managertask.family.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientDtoMapper clientDtoMapper;
    private final FamilyRepository familyRepository;

    private final InvitationRepository invitationRepository;

     Optional<ClientDto> getUserById(Long id) {
        return clientRepository.findById(id)
                .map(clientDtoMapper::map);
    }
    public Optional<Client> getUserByEmail(String email){
       return clientRepository.findClientByEmail(email);
    }

    Optional<ClientDto> getUserForCurrentUser(Long id){

        String currentUserName = getCurrentUsername();

        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent() && client.get().getEmail().equals(currentUserName)) {
            return client.map(clientDtoMapper::map);
        } else return Optional.empty();
    }
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    void updateUser(ClientDto clientDto){
        Client client = clientDtoMapper.map(clientDto);
        if(client.getEmail().equals(getCurrentUsername())) {
                clientRepository.save(client);
        }else {
            throw new IllegalArgumentException();
        }
    }
    void inviteClientToFamily(ClientDto inviterDto, ClientDto inviteeDto){
        Client inviter = clientDtoMapper.map(inviterDto);
        Client invitee = clientDtoMapper.map(inviteeDto);
        Family family = familyRepository.findById(inviter.getFamily().getId()).orElseThrow();

        var invitation = Invitation.builder()
                .inviter(inviter)
                .invitee(invitee)
                .family(family)
                .createdDate(LocalDateTime.now())
                .build();
        if(invitationRepository.findByFamilyAndInvitee(family,invitee).isEmpty()) {
            invitationRepository.save(invitation);
        } //todo think about throwing exceptions
    }
     void joinFamily(Long inviteeId, Long familyId){
        Family family = familyRepository.findById(familyId).orElseThrow();
        Client invitee = clientRepository.findById(inviteeId).orElseThrow();
        Invitation invitation = invitationRepository.findByFamilyAndInvitee(family, invitee).orElseThrow();
        if(invitee.getFamily()==null){
         invitee.setFamily(invitation.getFamily());
         clientRepository.save(invitee);
         invitationRepository.delete(invitation);
        }else return;
    }
}
