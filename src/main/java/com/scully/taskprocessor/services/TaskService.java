package com.scully.taskprocessor.services;

import com.scully.taskprocessor.exceptions.TaskNotFoundException;
import com.scully.taskprocessor.models.TaskDTO;
import com.scully.taskprocessor.models.TaskEntity;
import com.scully.taskprocessor.models.TaskRecordEntity;
import com.scully.taskprocessor.repositories.TaskRecordRepository;
import com.scully.taskprocessor.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
public class TaskService {

  private final TaskRepository taskRepository;
  private final TaskRecordRepository taskRecordRepository;

  public TaskService(TaskRepository taskRepository, TaskRecordRepository taskRecordRepository) {
    this.taskRepository = taskRepository;
    this.taskRecordRepository = taskRecordRepository;
  }

  public TaskEntity processTask(TaskDTO taskDTO, String userId) {
    log.info("Processing task: {}", taskDTO);

    TaskEntity taskEntity = taskRepository.findFirstByNameAndUserId(taskDTO.getName().toLowerCase(), userId)
            .orElse(new TaskEntity(null, taskDTO.getName().toLowerCase(), userId, taskDTO.getDurationMs(), taskDTO.getDurationMs()));

    if (taskEntity.getId() == null) {
      taskEntity = taskRepository.save(taskEntity);
    }
    TaskRecordEntity taskRecordEntity = new TaskRecordEntity(null, taskEntity.getId(), taskDTO.getDurationMs(), LocalDateTime.now());
    taskRecordRepository.save(taskRecordEntity);
    Long average = taskRecordRepository.getAverage(taskEntity.getId());
    taskEntity.setAverageDurationMs(average);

    return taskRepository.save(taskEntity);
  }

  public Long getAverageDurationForTaskByName(String taskName, String userId) {
    return taskRepository.findFirstByNameAndUserId(taskName.toLowerCase(), userId)
            .orElseThrow(() -> new TaskNotFoundException("No task found with the identifier [" + taskName + "]"))
            .getAverageDurationMs();
  }
}
