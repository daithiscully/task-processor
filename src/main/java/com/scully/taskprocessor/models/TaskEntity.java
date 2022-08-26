package com.scully.taskprocessor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class TaskEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String userId;
  private Long currentDurationMs;
  private Long averageDurationMs;
}
