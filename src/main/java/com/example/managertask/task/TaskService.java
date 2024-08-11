package com.example.managertask.task;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskDtoMapper taskDtoMapper;

    public TaskService(TaskRepository taskRepository, TaskDtoMapper taskDtoMapper) {
        this.taskRepository = taskRepository;
        this.taskDtoMapper = taskDtoMapper;
    }

    Optional<TaskDto> getTaskById(Long id){
        return taskRepository.findById(id)
                .map(taskDtoMapper::map);
    }

    TaskDto saveTask(TaskDto taskDto) {
        Task taskToSave = taskDtoMapper.map(taskDto);
        Task savedTask = taskRepository.save(taskToSave);
        return taskDtoMapper.map(savedTask);
    }
}
