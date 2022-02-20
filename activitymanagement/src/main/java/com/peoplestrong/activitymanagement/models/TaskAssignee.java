package com.peoplestrong.activitymanagement.models;

import javax.persistence.*;

@Entity
@Table(name = "task_assignee")
public class TaskAssignee {
    @EmbeddedId
    private TaskAssigneeKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "status")
    private String status;

    public TaskAssignee() {
    }

    public TaskAssignee(TaskAssigneeKey id, User user, Task task, String status) {
        this.id = id;
        this.user = user;
        this.task = task;
        this.status = status;
    }

    public TaskAssigneeKey getId() {
        return id;
    }

    public void setId(TaskAssigneeKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TaskAssignee other = (TaskAssignee) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
