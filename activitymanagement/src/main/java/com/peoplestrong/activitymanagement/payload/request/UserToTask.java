package com.peoplestrong.activitymanagement.payload.request;

public class UserToTask {
    private Long userId;
    private Long taskId;
    private String status;

    public UserToTask(Long userId, Long taskId, String status) {
        this.userId = userId;
        this.taskId = taskId;
        this.status = status;
    }

    public UserToTask() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
