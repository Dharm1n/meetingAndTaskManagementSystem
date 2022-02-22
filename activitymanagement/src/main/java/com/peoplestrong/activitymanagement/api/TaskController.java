package com.peoplestrong.activitymanagement.api;

import com.peoplestrong.activitymanagement.models.Task;
import com.peoplestrong.activitymanagement.models.TaskAssignee;
import com.peoplestrong.activitymanagement.models.User;
import com.peoplestrong.activitymanagement.payload.request.*;
import com.peoplestrong.activitymanagement.payload.response.*;
import com.peoplestrong.activitymanagement.repo.TaskRepo;
import com.peoplestrong.activitymanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RestController @RequiredArgsConstructor @RequestMapping("/api") @Slf4j @PreAuthorize("hasRole('ROLE_USER')") @Transactional
public class TaskController {

    @Autowired
    TaskRepo taskRepo;

    @Autowired
    TaskService taskService;

    @Autowired
    UserNotFound userNotFound;

    @Autowired
    TaskNotFound taskNotFound;

    @Autowired
    NoAuthority noAuthority;
//    --------------   CREATE TASK   ------------------------------------------------------------------------------------------------
    //Add a task
    @PostMapping("/task")
    public ResponseEntity<?> addTask(@RequestBody AddTask addTask)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Task task=new Task(null,
                addTask.getTitle(),
                addTask.getCreator(),
                LocalDateTime.parse(addTask.getCreationTime(), formatter),
                LocalDateTime.parse(addTask.getDeadline(), formatter),
                addTask.getDescription()
                );
        taskRepo.save(task);
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/task").toUriString());

        for(Long userid:addTask.getTaskAssignees())
        {
            addUserToTask(new UserToTask(userid,
                    task.getId(),
                    "To do"
            ));
        }
        return ResponseEntity.created(uri).body(new IdResponse(task.getId()));
    }

//   ----------------   UPDATE TASK ------------------------------------------------------------------------------------------------
    //Add a user to task
    private ResponseEntity<?> returnByStatus(int status)
    {
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body(taskNotFound);
        else if(status==2)
            return ResponseEntity.badRequest().body(userNotFound);
        else if(status==3)
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User is already assigned to that task."));
        else
            return ResponseEntity.badRequest().body(new MessageResponse("Some error occurred please try again"));

    }

    @PostMapping("/task/adduser")
    public ResponseEntity<?> addUserToTask(@RequestBody UserToTask userToTask)
    {
        int status=taskService.addUserToTask(userToTask);
        return returnByStatus(status);
    }

    @PutMapping("/task/{userid}")
    public ResponseEntity<?> updateTask(@PathVariable(name = "userid") Long userid,@RequestBody Task task)
    {
        if(!Objects.equals(userid, task.getCreator()))
            return ResponseEntity.badRequest().body(noAuthority);
        int status=taskService.updateTask(task);
        return returnByStatus(status);
    }


    @PutMapping("/task/creator/{userid}")
    public ResponseEntity<?> updateTaskCreator(@PathVariable(name = "userid") Long userid,@RequestBody Task task)
    {
        if(!Objects.equals(userid, task.getCreator()))
            return ResponseEntity.badRequest().body(noAuthority);
        int status=taskService.updateTaskCreator(task);
        return returnByStatus(status);
    }


    @PutMapping("/task/deadline/{userid}")
    public ResponseEntity<?> updateTaskDeadline(@PathVariable(name = "userid") Long userid,@RequestBody Task task)
    {
        if(!Objects.equals(userid, task.getCreator()))
            return ResponseEntity.badRequest().body(noAuthority);
        int status=taskService.updateTaskDeadline(task);
        return returnByStatus(status);
    }

    @PutMapping("/task/description/{userid}")
    public ResponseEntity<?> updateTaskDescription(@PathVariable(name = "userid") Long userid,@RequestBody Task task)
    {
        if(!Objects.equals(userid, task.getCreator()))
            return ResponseEntity.badRequest().body(noAuthority);
        int status=taskService.updateTaskDescription(task);
        return returnByStatus(status);
    }


    @PutMapping("/task/title/{userid}")
    public ResponseEntity<?> updateTaskTitle(@PathVariable(name = "userid") Long userid,@RequestBody Task task)
    {
        if(!Objects.equals(userid, task.getCreator()))
            return ResponseEntity.badRequest().body(noAuthority);
        int status=taskService.updateTaskTitle(task);
        return returnByStatus(status);
    }


    @PutMapping("/task/status/{userid}")
    public ResponseEntity<?> updateTaskStatus(@PathVariable(name = "userid") Long userid,@RequestBody UserToTask userToTask)
    {
        int status=taskService.updateTaskStatus(userid,userToTask);
        return returnByStatus(status);
    }

//   ----------------   DELETE TASK ------------------------------------------------------------------------------------------------
    //Delete a task by id
    @DeleteMapping("/task/{userid}")
    public ResponseEntity<?> deleteTaskById(@PathVariable(name = "userid") Long userid, @RequestBody DeleteTaskRequest deleteTaskRequest)
    {
        if(!Objects.equals(userid, deleteTaskRequest.getCreatorId()))
            return ResponseEntity.badRequest().body(noAuthority);
        int status=taskService.deleteTaskById(deleteTaskRequest.getTaskId());
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body(taskNotFound);
        else
            return ResponseEntity.badRequest().body(new MessageResponse("Some error occurred please try again"));
    }

    @DeleteMapping("/task/user/{userid}")
    public ResponseEntity<?> deleteUserFromMeeting(@PathVariable(name = "userid") Long userid, @RequestBody DeleteUserFromTask deleteUserFromTask)
    {
        int status=taskService.deleteUserFromTask(deleteUserFromTask,userid);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body(taskNotFound);
        else if(status==2)
            return ResponseEntity.badRequest().body(noAuthority);
        else if(status==3)
            return ResponseEntity.badRequest().body(userNotFound);
        else if(status==4)
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User already removed from the task."));
        else
            return ResponseEntity.badRequest().body(new MessageResponse("Some error occurred please try again"));

    }
//   ----------------   READ TASK ------------------------------------------------------------------------------------------------
    @GetMapping("/task/creator/{userid}")
    public ResponseEntity<?> getTasksCreatedBy(@PathVariable(name = "userid") Long userid)
    {
        return taskService.findTaskByCreatorid(userid);
    }

    @GetMapping("/task/noninvited")
    public ResponseEntity<?> findAllNonInvitedUsers(@RequestParam Long userId,@RequestParam Long taskId)
    {
        return taskService.findAllNonInvitedUsers(userId,taskId);
    }

    @GetMapping("/task/user/{userid}")
    public ResponseEntity<?> getAllTasksByUserid(@PathVariable(name = "userid") Long userid)
    {
        return taskService.getAllTasksByUserid(userid);
    }

}
