package com.example.managertask.client.invite;

import com.example.managertask.client.Client;
import com.example.managertask.client.ClientRepository;
import com.example.managertask.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final InvitationDtoMapper invitationDtoMapper;
    
    List<InvitationDto> getAllUserInvitation(){
        Client client = clientRepository.findClientByEmail(clientService.getCurrentUsername()).orElseThrow();
        List<Invitation> allByInviteeId = invitationRepository.findAllByInviteeId(client.getId());
        return allByInviteeId.stream().map(invitationDtoMapper::map).toList();
    }
}
