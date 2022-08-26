package com.scully.taskprocessor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {
  private Long id;
  private String userId;
  private Long currentDurationMs;
  private Long averageDurationMs;
}
