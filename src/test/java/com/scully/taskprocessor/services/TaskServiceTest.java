package com.scully.taskprocessor.services;

import com.scully.taskprocessor.models.TaskDTO;
import com.scully.taskprocessor.models.TaskEntity;
import com.scully.taskprocessor.models.TaskRecordEntity;
import com.scully.taskprocessor.repositories.TaskRecordRepository;
import com.scully.taskprocessor.repositories.TaskRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskServiceTest {

  @Mock
  TaskRepository taskRepository;
  @Mock
  TaskRecordRepository taskRecordRepository;


  @InjectMocks
  TaskService taskService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testProcessTask() {
    // given
    TaskDTO taskDTO = new TaskDTO();

    when(taskRepository.findFirstByNameAndUserId(taskDTO.getName(), "userId")).thenReturn(Optional.empty());
    when(taskRepository.save(any(TaskEntity.class))).thenReturn(getTaskEntity());
    when(taskRecordRepository.save(any(TaskRecordEntity.class))).thenReturn(new TaskRecordEntity());
    // when
    TaskEntity actual = taskService.processTask(taskDTO, "userId");
    // then
    assertThat(actual).isNotNull();
    verify(taskRepository, times(2)).save(any(TaskEntity.class));
    verify(taskRecordRepository).save(any(TaskRecordEntity.class));
  }

  @Test
  void testProcessTask_whenTaskAlreadyExistsInDatabase() {
    // given
    TaskDTO taskDTO = new TaskDTO("name", 500L);

    when(taskRepository.findFirstByNameAndUserId(taskDTO.getName(), "userId")).thenReturn(Optional.of(getTaskEntity()));
    when(taskRepository.save(any(TaskEntity.class))).thenReturn(new TaskEntity());
    when(taskRecordRepository.save(any(TaskRecordEntity.class))).thenReturn(new TaskRecordEntity());
    // when
    TaskEntity actual = taskService.processTask(taskDTO, "userId");
    // then
    assertThat(actual).isNotNull();
    verify(taskRepository).save(any(TaskEntity.class));
    verify(taskRecordRepository).save(any(TaskRecordEntity.class));
  }

  @Test
  void testProcessTask_averageDurationCalculationLogicIsCorrect() {
    // given
    TaskDTO taskDTO1 = new TaskDTO("taskName", 500L);
    TaskDTO taskDTO2 = new TaskDTO("taskName", 100L);

    TaskEntity taskEntityInDb = getTaskEntity();
    when(taskRepository.findFirstByNameAndUserId(taskDTO1.getName(), "userId")).thenReturn(Optional.of(taskEntityInDb));
    when(taskRepository.findFirstByNameAndUserId(taskDTO1.getName(), "userId")).thenReturn(Optional.of(taskEntityInDb));
    when(taskRepository.save(any(TaskEntity.class))).thenReturn(new TaskEntity());
    when(taskRecordRepository.save(any(TaskRecordEntity.class))).thenReturn(getTaskRecordEntity());
    when(taskRecordRepository.getAverage(1L)).thenReturn(300L);
    // when
    taskService.processTask(taskDTO1, "userId");
    taskService.processTask(taskDTO2, "userId");
    // then
    assertThat(taskEntityInDb.getAverageDurationMs()).isEqualTo(300L);
  }

  // Helper methods
  private TaskEntity getTaskEntity() {
    return new TaskEntity(1L, "taskName", "userId", 100L, 500L);
  }

  private TaskRecordEntity getTaskRecordEntity() {
    return new TaskRecordEntity(1L, 1L, 500L, LocalDateTime.now());
  }

}
