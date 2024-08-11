package com.example.managertask.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
 class TaskController {
    private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("task/{id}")
    ResponseEntity<TaskDto> getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/task")
    ResponseEntity<?> saveTask(@RequestBody TaskDto taskDto){
        TaskDto dto = taskService.saveTask(taskDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    @GetMapping("/client/task/{id}")
    ResponseEntity<List<TaskDto>> getAllUserTask(@PathVariable Long id){
        if(taskService.getTasksByUserId(id).isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(taskService.getTasksByUserId(id));
    }
}
