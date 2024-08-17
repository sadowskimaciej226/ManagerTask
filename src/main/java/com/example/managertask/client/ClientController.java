package com.example.managertask.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
 class ClientController {

 private final ClientService clientService;
 private final ObjectMapper objectMapper;

    @GetMapping("user/{id}")
 ResponseEntity<ClientDto> getUserById(@PathVariable Long id){
     return clientService.getUserForCurrentUser(id)
             .map(ResponseEntity::ok)
             .orElse(ResponseEntity.notFound().build());
    }
    @PatchMapping("/user/{id}")
    ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody JsonMergePatch patch){
        try {
            ClientDto client = clientService.getUserById(id).orElseThrow();
            ClientDto clientPatched = applyPatch(client, patch);
            if(clientPatched.getFamilyId()==null){
                clientService.updateUser(clientPatched);
            }else return ResponseEntity.unprocessableEntity().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private ClientDto applyPatch(ClientDto client, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode clientNode = objectMapper.valueToTree(client);
        JsonNode clientPatchedNode = patch.apply(clientNode);
        return objectMapper.treeToValue(clientPatchedNode, ClientDto.class);
    }
    @PostMapping("/inviter/{inviterId}/invitee/{inviteeId}")
    ResponseEntity<?> inviteClientToFamily(@PathVariable Long inviterId,
                                           @PathVariable Long inviteeId){
        try {
            ClientDto inviter = clientService.getUserForCurrentUser(inviterId).orElseThrow();
            ClientDto invitee = clientService.getUserById(inviteeId).orElseThrow();
            clientService.inviteClientToFamily(inviter, invitee);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
