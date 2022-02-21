package com.peoplestrong.activitymanagement.payload.response;

import org.springframework.stereotype.Component;

@Component
public class MeetingNotFound {
    String errorMessage="Meeting not found.";

    public MeetingNotFound() {
    }

    public MeetingNotFound(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
