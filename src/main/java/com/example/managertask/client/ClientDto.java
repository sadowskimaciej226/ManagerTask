package com.example.managertask.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
 class ClientDto {
    private String firstName;
    private String lastName;
    private Role role;
    private int points;
    private String email;
    private Long familyId;
    private String familyName;

}
