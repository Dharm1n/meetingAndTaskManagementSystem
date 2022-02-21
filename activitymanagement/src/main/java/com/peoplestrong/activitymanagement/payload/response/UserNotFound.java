package com.peoplestrong.activitymanagement.payload.response;

import org.springframework.stereotype.Component;

@Component
public class UserNotFound {
    String errorMessage="Error: User doesn't exist.";

    public UserNotFound(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public UserNotFound() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
