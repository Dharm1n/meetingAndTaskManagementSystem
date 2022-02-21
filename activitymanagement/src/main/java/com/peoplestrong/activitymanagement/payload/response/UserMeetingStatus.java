package com.peoplestrong.activitymanagement.payload.response;

public class UserMeetingStatus {
    private String username;
    private String name;
    private String status;

    public UserMeetingStatus(String username, String name, String status) {
        this.username = username;
        this.name = name;
        this.status = status;
    }

    public UserMeetingStatus() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
