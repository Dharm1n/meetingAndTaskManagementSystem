package com.peoplestrong.activitymanagement.payload.response;

import org.springframework.stereotype.Component;

@Component
public class MeetingNotFound {
    String message="Meeting not found.";

    public MeetingNotFound() {
    }

    public MeetingNotFound(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
