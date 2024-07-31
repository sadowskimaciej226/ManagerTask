package com.example.managertask.task;

import org.springframework.stereotype.Service;

@Service
public class TaskDtoMapper {
    TaskDto map(Task task){
        TaskDto dto = new TaskDto();
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setExpirationTime(task.getExpirationTime());
        dto.setStartTime(task.getStartTime());
        dto.setDone(task.isDone());
        dto.setUsername(task.getClient().getFirstName());
        dto.setUser_id(task.getClient().getId());
        return dto;
    }
}
