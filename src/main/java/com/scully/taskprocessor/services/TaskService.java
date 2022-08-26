package com.scully.taskprocessor.services;

import com.scully.taskprocessor.models.TaskDTO;
import com.scully.taskprocessor.models.TaskEntity;
import com.scully.taskprocessor.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public TaskEntity processTask(TaskDTO taskDTO, String userId) {
    log.info("Processing task: {}", taskDTO);
    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setUserId(userId);
    taskEntity.setCurrentDurationMs(taskDTO.getDurationMs());

    return taskRepository.save(taskEntity);
  }

}
