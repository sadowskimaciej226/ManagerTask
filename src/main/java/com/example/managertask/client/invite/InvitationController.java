package com.example.managertask.client.invite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
 class InvitationController {
    private final InvitationService invitationService;

    @GetMapping("/user/invitation")
    ResponseEntity<List<InvitationDto>> getAllUserInvitation(){
        if(invitationService.getAllUserInvitation().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invitationService.getAllUserInvitation());
    }
}
