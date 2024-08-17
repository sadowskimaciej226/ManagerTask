package com.example.managertask.client.invite;

import org.springframework.stereotype.Service;

@Service
public class InvitationDtoMapper {
    InvitationDto map(Invitation invitation){
        InvitationDto dto = new InvitationDto();
        dto.setInviterId(invitation.getInviter().getId());
        dto.setInviteeId(invitation.getInvitee().getId());
        dto.setFamilyId(invitation.getFamily().getId());
        return dto;
    }
}
