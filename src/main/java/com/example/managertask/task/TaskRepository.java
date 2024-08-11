package com.example.managertask.task;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByClientId(Long id);
}
