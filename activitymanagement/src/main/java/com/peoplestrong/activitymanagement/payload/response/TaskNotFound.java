package com.peoplestrong.activitymanagement.payload.response;

import org.springframework.stereotype.Component;

@Component
public class TaskNotFound {
    String errorMessage="Task doesn't exist.";

    public TaskNotFound() {
    }

    public TaskNotFound(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
