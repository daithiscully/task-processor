package com.scully.taskprocessor.controllers;

import com.scully.taskprocessor.models.TaskDTO;
import com.scully.taskprocessor.models.TaskEntity;
import com.scully.taskprocessor.services.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskControllerTest {

  @Mock
  TaskService taskService;
  @InjectMocks
  TaskController taskController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAddTaskDuration() {
    // given
    when(taskService.processTask(any(TaskDTO.class), anyString()))
            .thenReturn(new TaskEntity(1L, "userId", 1L, 1L));

    // when
    String actual = taskController.addTaskDuration(new TaskDTO(1L, 1L), "userId");

    // then
    assertThat(actual).isEqualTo("Task duration accepted");

  }
}
