package com.peoplestrong.activitymanagement.payload.request;

public class DeleteUserFromTask {

    private Long taskId;
    private Long userToBeDeleted;

    public DeleteUserFromTask(Long taskId, Long userToBeDeleted) {
        this.taskId = taskId;
        this.userToBeDeleted = userToBeDeleted;
    }

    public DeleteUserFromTask() {
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserToBeDeleted() {
        return userToBeDeleted;
    }

    public void setUserToBeDeleted(Long userToBeDeleted) {
        this.userToBeDeleted = userToBeDeleted;
    }
}
