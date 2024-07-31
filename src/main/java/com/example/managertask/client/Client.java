package com.example.managertask.client;

import com.example.managertask.family.Family;
import com.example.managertask.task.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private int points;
    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;
    @OneToMany(mappedBy = "client")
    private List<Task> tasks = new ArrayList<>();

}
