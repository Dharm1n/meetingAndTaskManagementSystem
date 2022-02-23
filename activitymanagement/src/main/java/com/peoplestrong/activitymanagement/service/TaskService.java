package com.peoplestrong.activitymanagement.service;

import com.peoplestrong.activitymanagement.models.Task;
import com.peoplestrong.activitymanagement.models.TaskAssignee;
import com.peoplestrong.activitymanagement.payload.request.DeleteUserFromTask;
import com.peoplestrong.activitymanagement.payload.request.UserToTask;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    int addUserToTask(Long userid,UserToTask userToTask);

    int updateTask(Long userid,Task task);

    int updateTaskCreator(Long userid,Task task);

    int updateTaskDeadline(Long userid,Task task);

    int updateTaskTitle(Long userid,Task task);

    int updateTaskDescription(Long userid,Task task);

    int deleteTaskById(Long userid,Long taskid);

    ResponseEntity<?> findTaskByCreatorid(Long userid);

    ResponseEntity<?> getAllTasksByUserid(Long userid);

    int updateTaskStatus(Long userid, UserToTask userToTask);

    int deleteUserFromTask(DeleteUserFromTask deleteUserFromTask, Long userid);

    ResponseEntity<?> findAllNonInvitedUsers(Long userId, Long taskId);
}
