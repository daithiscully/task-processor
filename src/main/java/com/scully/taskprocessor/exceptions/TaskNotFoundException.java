package com.scully.taskprocessor.exceptions;

public class TaskNotFoundException extends RuntimeException {

  public TaskNotFoundException(String message) {
    super(message);
  }
}
