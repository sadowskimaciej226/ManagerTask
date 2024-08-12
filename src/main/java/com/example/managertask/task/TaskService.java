package com.example.managertask.task;

import com.example.managertask.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskDtoMapper taskDtoMapper;


    Optional<TaskDto> getTaskById(Long id){
        return taskRepository.findById(id)
                .map(taskDtoMapper::map);
    }

    TaskDto saveTask(TaskDto taskDto) {
        Task taskToSave = taskDtoMapper.map(taskDto);
        Task savedTask = taskRepository.save(taskToSave);
        return taskDtoMapper.map(savedTask);
    }
    List<TaskDto> getTasksByUserId(Long id){
        List<Task> clientTasks = taskRepository.findByClientId(id);
       return clientTasks.stream().map(taskDtoMapper::map).toList();
    }
    void updateTask(TaskDto taskDto){
        Task toUpdate = taskDtoMapper.map(taskDto);
        taskRepository.save(toUpdate);
    }
    void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

}
