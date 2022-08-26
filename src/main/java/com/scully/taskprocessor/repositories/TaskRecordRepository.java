package com.scully.taskprocessor.repositories;

import com.scully.taskprocessor.models.TaskRecordEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRecordRepository extends CrudRepository<TaskRecordEntity, Long> {

  @Query("SELECT avg(tr.duration_ms) FROM task_records tr WHERE tr.task_id = :taskId")
  Long getAverage(@Param("taskId") Long taskId);

  Optional<TaskRecordEntity> findAllByTaskId(Long taskId);

}
