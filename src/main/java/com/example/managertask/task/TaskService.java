package com.example.managertask.task;


import com.example.managertask.client.Client;
import com.example.managertask.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskDtoMapper taskDtoMapper;
    private final ClientRepository clientRepository;


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
        LocalDateTime now = LocalDateTime.now();
        List<Task> clientTasks = taskRepository
                .findByExpirationTimeAfterAndClientId(now, id);
       return clientTasks.stream().map(taskDtoMapper::map).toList();
    }
    List<TaskDto> getAllTaskByFamilyId(Long id){
        List<Client> clientsInFamily = clientRepository.findAllByFamilyId(id);
        return clientsInFamily
                .stream().map(client -> {
                    return client.getTasks().stream().map(taskDtoMapper::map).toList();
                })
                .flatMap(Collection::stream)
                .toList();

    }
    void updateTask(TaskDto taskDto){
        Task toUpdate = taskDtoMapper.map(taskDto);
        taskRepository.save(toUpdate);
    }
    void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
    void setNewExpirationTime(Periodicity periodicity, long hours){
        LocalDateTime now = LocalDateTime.now();
        List<Task> taskToChange = taskRepository
                .findByPeriodicityAndExpirationTimeAfter(periodicity, now);
        taskToChange.forEach(task -> {
            task.setExpirationTime(now.plusHours(hours));
            taskRepository.save(task);
        });
    }


    @Scheduled(cron = "0 0 0 * * ?")
    void updateExpirationTimeEveryDay(){
       setNewExpirationTime(Periodicity.EVERY_DAY, 24);
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    void updateExpirationTimeEverySunday(){
        setNewExpirationTime(Periodicity.EVERY_WEEK, 24*7);
    }
    @Scheduled(cron = "0 0 0 1 * ?")
    void updateExpirationTimeEveryMonth(){
        setNewExpirationTime(Periodicity.EVERY_MONTH, 24*31);
    }


}
