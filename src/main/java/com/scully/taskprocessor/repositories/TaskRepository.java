package com.scully.taskprocessor.repositories;

import com.scully.taskprocessor.models.TaskEntity;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
}
