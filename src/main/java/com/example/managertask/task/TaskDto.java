package com.example.managertask.task;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime expirationTime;
    private LocalDateTime startTime;
    private Periodicity periodicity;
    private boolean done;
    private Long user_id;
    private String username;
}
