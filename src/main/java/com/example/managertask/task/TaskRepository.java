package com.example.managertask.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByExpirationTimeAfterAndClientId(LocalDateTime now,Long id);
    List<Task> findByPeriodicityAndExpirationTimeAfter(Periodicity periodicity, LocalDateTime now);
    @Query("SELECT t FROM Task t where t.client.email = ?1 AND ((t.startTime > ?2 OR t.expirationTime < ?3) " +
            "OR t.periodicity = com.example.managertask.task.Periodicity.EVERY_DAY)")
    List<Task> findCurrentTasks(String email, LocalDateTime start, LocalDateTime expiration);
    List<Task> findAllByPeriodicity(Periodicity periodicity);

 }
