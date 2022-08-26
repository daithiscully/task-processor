package com.scully.taskprocessor.controllers;

import com.scully.taskprocessor.exceptions.TaskNotFoundException;
import com.scully.taskprocessor.models.TaskDTO;
import com.scully.taskprocessor.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping("/add")
  public String addTaskDuration(@RequestBody TaskDTO taskDTO, @RequestHeader("x-user-id") String userId) {
    taskService.processTask(taskDTO, userId);
    return "Task duration accepted";
  }

  @GetMapping("/average")
  public Long getAverageDurationForTask(@RequestParam("taskName") String taskName, @RequestHeader("x-user-id") String userId) {
    return taskService.getAverageDurationForTaskByName(taskName, userId);
  }

  @ExceptionHandler(TaskNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleTaskNotFoundExceptions(TaskNotFoundException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }
}
