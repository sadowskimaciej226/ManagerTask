package com.example.managertask.client.invite;

import com.example.managertask.client.Client;
import com.example.managertask.family.Family;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdDate;
    @ManyToOne
    private Client inviter;
    @ManyToOne
    private Client invitee;
    @ManyToOne
    private Family family;
}
