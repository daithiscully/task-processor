package com.scully.taskprocessor.services;

import com.scully.taskprocessor.models.TaskDTO;
import com.scully.taskprocessor.models.TaskEntity;
import com.scully.taskprocessor.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskServiceTest {

  @Mock
  TaskRepository taskRepository;


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

    when(taskRepository.save(any(TaskEntity.class))).thenReturn(new TaskEntity());
    // when
    TaskEntity actual = taskService.processTask(taskDTO, "userId");
    // then
    assertThat(actual).isNotNull();
    verify(taskRepository).save(any(TaskEntity.class));
  }
}
