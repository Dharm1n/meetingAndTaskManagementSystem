package com.peoplestrong.activitymanagement.payload.response;

public class UserNameEmail {
    private Long userId;
    private String userName;
    private String fullName;

    public UserNameEmail() {
    }

    public UserNameEmail(Long userId, String userName, String fullName) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
