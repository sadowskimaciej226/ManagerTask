package com.example.managertask.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientDto {
    private String firstName;
    private String lastName;
    private String role;
    private int points;
    private String email;
    private Long familyId;
    private String familyName;

}
