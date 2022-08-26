package com.scully.taskprocessor.controllers;

import com.scully.taskprocessor.models.TaskDTO;
import com.scully.taskprocessor.services.TaskService;
import org.springframework.web.bind.annotation.*;

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

}
