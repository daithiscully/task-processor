package com.scully.taskprocessor.services;

import com.scully.taskprocessor.exceptions.TaskNotFoundException;
import com.scully.taskprocessor.models.TaskDTO;
import com.scully.taskprocessor.models.TaskEntity;
import com.scully.taskprocessor.models.TaskRecordEntity;
import com.scully.taskprocessor.repositories.TaskRecordRepository;
import com.scully.taskprocessor.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class TaskServiceTest {

  @Mock
  TaskRepository taskRepository;
  @Mock
  TaskRecordRepository taskRecordRepository;


  @InjectMocks
  TaskService underTest;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testProcessTask() {
    // given
    TaskDTO taskDTO = new TaskDTO("task", 200L);

    when(taskRepository.findFirstByNameAndUserId(taskDTO.getName(), "userId")).thenReturn(Optional.empty());
    when(taskRepository.save(any(TaskEntity.class))).thenReturn(getTaskEntity());
    when(taskRecordRepository.save(any(TaskRecordEntity.class))).thenReturn(new TaskRecordEntity());
    // when
    TaskEntity actual = underTest.processTask(taskDTO, "userId");
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
    TaskEntity actual = underTest.processTask(taskDTO, "userId");
    // then
    assertThat(actual).isNotNull();
    verify(taskRepository).save(any(TaskEntity.class));
    verify(taskRecordRepository).save(any(TaskRecordEntity.class));
  }

  @Test
  void testGetAverageDurationForTaskByName() {
    // given
    when(taskRepository.findFirstByNameAndUserId("task", "user"))
            .thenReturn(Optional.of(getTaskEntity()));
    // when
    Long actual = underTest.getAverageDurationForTaskByName("task", "user");
    // then
    assertThat(actual).isEqualTo(500L);
  }

  @Test
  void testGetAverageDurationForTaskByName_notFound() {
    // given
    when(taskRepository.findFirstByNameAndUserId("task", "user"))
            .thenReturn(Optional.empty());
    // when
    assertThatThrownBy(() -> {
      underTest.getAverageDurationForTaskByName("task", "user");
    })
            .isInstanceOf(TaskNotFoundException.class)
            .hasMessage("No task found with the identifier [task]");
    // then
  }

  // Helper methods
  private TaskEntity getTaskEntity() {
    return new TaskEntity(1L, "taskName", "userId", 100L, 500L);
  }

  private TaskRecordEntity getTaskRecordEntity() {
    return new TaskRecordEntity(1L, 1L, 500L, LocalDateTime.now());
  }

}
