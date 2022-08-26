package com.scully.taskprocessor.controllers;

import com.scully.taskprocessor.exceptions.TaskNotFoundException;
import com.scully.taskprocessor.models.TaskDTO;
import com.scully.taskprocessor.models.TaskEntity;
import com.scully.taskprocessor.repositories.TaskRecordRepository;
import com.scully.taskprocessor.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskControllerTest {

  @Mock
  TaskService taskService;
  @InjectMocks
  TaskController underTest;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAddTaskDuration() {
    // given
    when(taskService.processTask(any(TaskDTO.class), anyString()))
            .thenReturn(new TaskEntity(1L, "taskName", "userId", 1L, 1L));

    // when
    String actual = underTest.addTaskDuration(new TaskDTO("name", 1L), "userId");

    // then
    assertThat(actual).isEqualTo("Task duration accepted");
  }

  @Test
  void testGetAverageDurationForTask() {
    // given
    when(taskService.getAverageDurationForTaskByName("task", "user"))
            .thenReturn(500L);

    // when
    Long actual = underTest.getAverageDurationForTask("task", "user");

    // then
    assertThat(actual).isEqualTo(500L);
  }

  @Test
  void testHandleTaskNotFoundExceptions() {
    // given
    TaskNotFoundException taskNotFoundException = new TaskNotFoundException("exception message");

    // when
    ResponseEntity<Map<String, Object>> actual = underTest.handleTaskNotFoundExceptions(taskNotFoundException);
    // then
    assertThat(actual).isNotNull();
    assertThat(actual.getBody())
            .isNotNull()
            .hasFieldOrPropertyWithValue("message", "exception message");
  }
}
