package com.peoplestrong.activitymanagement.payload.request;

public class DeleteTaskRequest {
    private Long taskId;
    private Long creatorId;

    public DeleteTaskRequest() {
    }

    public DeleteTaskRequest(Long taskId, Long creatorId) {
        this.taskId = taskId;
        this.creatorId = creatorId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
