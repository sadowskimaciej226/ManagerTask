package com.example.managertask.family;

import com.example.managertask.client.Client;
import com.example.managertask.client.invite.Invitation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "family")
@Getter
@Setter
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String familyName;
    @OneToMany(mappedBy = "family")
    private List<Client> clients = new ArrayList<>();
    @OneToMany(mappedBy = "family")
    private Set<Invitation> invitations;
}
