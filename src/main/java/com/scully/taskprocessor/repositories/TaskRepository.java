package com.scully.taskprocessor.repositories;

import com.scully.taskprocessor.models.TaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

  Optional<TaskEntity> findFirstByNameAndUserId(String name, String userId);
}
