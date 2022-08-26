package com.scully.taskprocessor.services;

import com.scully.taskprocessor.models.TaskDTO;
import com.scully.taskprocessor.models.TaskEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskService {

  public TaskEntity processTask(TaskDTO taskDTO) {
    log.info("Processing task: {}", taskDTO);

    return new TaskEntity();
  }

}
