package com.example.managertask.task;

import com.example.managertask.client.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title can't be null")
    private String title;
    private String description;
    @Future
    private LocalDateTime expirationTime;
    private LocalDateTime startTime;
    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
