package com.peoplestrong.activitymanagement.service;

import com.peoplestrong.activitymanagement.models.Task;
import com.peoplestrong.activitymanagement.models.TaskAssignee;
import com.peoplestrong.activitymanagement.payload.request.DeleteUserFromTask;
import com.peoplestrong.activitymanagement.payload.request.UserToTask;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    int addUserToTask(UserToTask userToTask);

    int updateTask(Task task);

    int updateTaskCreator(Task task);

    int updateTaskDeadline(Task task);

    int updateTaskTitle(Task task);

    int updateTaskDescription(Task task);

    int deleteTaskById(Long taskid);

    ResponseEntity<?> findTaskByCreatorid(Long userid);

    ResponseEntity<?> getAllTasksByUserid(Long userid);

    int updateTaskStatus(Long userid, UserToTask userToTask);

    int deleteUserFromTask(DeleteUserFromTask deleteUserFromTask, Long userid);
}
