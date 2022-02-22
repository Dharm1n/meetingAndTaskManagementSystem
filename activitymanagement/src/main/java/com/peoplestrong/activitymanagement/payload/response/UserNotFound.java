package com.peoplestrong.activitymanagement.payload.response;

import org.springframework.stereotype.Component;

@Component
public class UserNotFound {
    String message="Error: User doesn't exist.";

    public UserNotFound(String message) {
        this.message = message;
    }

    public UserNotFound() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
