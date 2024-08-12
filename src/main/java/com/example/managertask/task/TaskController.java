package com.example.managertask.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
 class TaskController {
    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    @GetMapping("/{id}")
    ResponseEntity<TaskDto> getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping()
    ResponseEntity<?> saveTask(@RequestBody TaskDto taskDto){
        TaskDto dto = taskService.saveTask(taskDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    @GetMapping("/client/{id}")
    ResponseEntity<List<TaskDto>> getAllUserTask(@PathVariable Long id){
        if(taskService.getTasksByUserId(id).isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(taskService.getTasksByUserId(id));
    }
    @PatchMapping("{id}")
    ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody JsonMergePatch patch){
        try {
            TaskDto task = taskService.getTaskById(id).orElseThrow();
            TaskDto taskPatched = applyPatch(task, patch);
            taskService.updateTask(taskPatched);
        }catch (JsonPatchException | JsonProcessingException e){
            return ResponseEntity.internalServerError().build();
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private TaskDto applyPatch(TaskDto task, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode taskNode = objectMapper.valueToTree(task);
        JsonNode taskPatchedNode = patch.apply(taskNode);
        return objectMapper.treeToValue(taskPatchedNode, TaskDto.class);
    }
}
