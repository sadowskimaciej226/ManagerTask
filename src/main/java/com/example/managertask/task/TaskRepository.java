package com.example.managertask.task;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByExpirationTimeAfterAndClientId(LocalDateTime now,Long id);
    List<Task> findByPeriodicityAndExpirationTimeAfter(Periodicity periodicity, LocalDateTime now);
 }
