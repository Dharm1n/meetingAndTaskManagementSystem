package com.peoplestrong.activitymanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.peoplestrong.activitymanagement.models.TaskAssignee;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddTask {

    private String title;
    private Long creator;
    private String creationTime;
    private String deadline;
    private String description;
    private List<Long> taskAssignees = new ArrayList<>();

    public AddTask() {
    }

    public AddTask(String title, Long creator, String creationTime, String deadline, String description, List<Long> taskAssignees) {
        this.title = title;
        this.creator = creator;
        this.creationTime = creationTime;
        this.deadline = deadline;
        this.description = description;
        this.taskAssignees = taskAssignees;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getTaskAssignees() {
        return taskAssignees;
    }

    public void setTaskAssignees(List<Long> taskAssignees) {
        this.taskAssignees = taskAssignees;
    }
}
