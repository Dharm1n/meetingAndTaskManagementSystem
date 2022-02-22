package com.peoplestrong.activitymanagement.payload.response;

import org.springframework.stereotype.Component;

@Component
public class NoAuthority {
    private String message="You Don't have authority to do this operation";

    public NoAuthority() {
    }

    public NoAuthority(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
