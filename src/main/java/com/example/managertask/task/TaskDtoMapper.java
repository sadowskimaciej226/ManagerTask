package com.example.managertask.task;

import com.example.managertask.client.Client;
import com.example.managertask.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskDtoMapper {
    private final ClientRepository clientRepository;
    TaskDto map(Task task){
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setExpirationTime(task.getExpirationTime());
        dto.setStartTime(task.getStartTime());
        dto.setPeriodicity(task.getPeriodicity());
        dto.setDone(task.isDone());
        if(task.getClient()!=null) {
            dto.setUsername(task.getClient().getFirstName());
            dto.setUser_id(task.getClient().getId());
        }
        return dto;
    }
    Task map(TaskDto dto){
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setExpirationTime(dto.getExpirationTime());
        task.setStartTime(dto.getStartTime());
        task.setDone(dto.isDone());
        task.setPeriodicity(dto.getPeriodicity());
        if(dto.getUser_id()!=null) {
            Client client = clientRepository.findById(dto.getUser_id()).orElseThrow();
            task.setClient(client);
        }
        return task;
    }

}
