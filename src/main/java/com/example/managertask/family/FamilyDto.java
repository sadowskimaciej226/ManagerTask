package com.example.managertask.family;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
 class FamilyDto {
    private Long id;
    private String familyName;
    private Set<Long> usersId;
    private List<String> usernames;
}
