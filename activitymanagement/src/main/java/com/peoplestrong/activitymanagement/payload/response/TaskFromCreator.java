package com.peoplestrong.activitymanagement.payload.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskFromCreator {
    private Long userid;
    private Long taskid;
    private String title;
    private String status;
    private Long creator;
    private LocalDateTime creationTime;
    private LocalDateTime deadline;
    private String description;
    private List<UserTaskStatus> userTaskStatuses=new ArrayList<>();
    private String creatorName;

    public TaskFromCreator() {
    }

    public TaskFromCreator(Long userid, Long taskid, String title, String status, Long creator, LocalDateTime creationTime, LocalDateTime deadline, String description, List<UserTaskStatus> userTaskStatuses, String creatorName) {
        this.userid = userid;
        this.taskid = taskid;
        this.title = title;
        this.status = status;
        this.creator = creator;
        this.creationTime = creationTime;
        this.deadline = deadline;
        this.description = description;
        this.userTaskStatuses = userTaskStatuses;
        this.creatorName = creatorName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public List<UserTaskStatus> getUserTaskStatuses() {
        return userTaskStatuses;
    }

    public void setUserTaskStatuses(List<UserTaskStatus> userTaskStatuses) {
        this.userTaskStatuses = userTaskStatuses;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
