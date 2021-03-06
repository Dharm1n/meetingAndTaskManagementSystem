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

@RestController @RequiredArgsConstructor @RequestMapping("/api") @Slf4j
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
            addUserToTask(addTask.getCreator(),new UserToTask(userid,
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
        else if(status==4)
            return ResponseEntity.badRequest().body(noAuthority);
        else
            return ResponseEntity.badRequest().body(new MessageResponse("Some error occurred please try again"));

    }

    @PostMapping("/task/adduser")
    public ResponseEntity<?> addUserToTask(@RequestParam(name = "userid") Long userid,@RequestBody UserToTask userToTask)
    {
        int status=taskService.addUserToTask(userid,userToTask);
        return returnByStatus(status);
    }

    @PutMapping("/task")
    public ResponseEntity<?> updateTask(@RequestParam(name = "userid") Long userid,@RequestBody Task task)
    {
        int status=taskService.updateTask(userid,task);
        return returnByStatus(status);
    }


    @PutMapping("/task/creator")
    public ResponseEntity<?> updateTaskCreator(@RequestParam(name = "userid") Long userid,@RequestBody Task task)
    {
        int status=taskService.updateTaskCreator(userid,task);
        return returnByStatus(status);
    }


    @PutMapping("/task/deadline")
    public ResponseEntity<?> updateTaskDeadline(@RequestParam(name = "userid") Long userid,@RequestBody Task task)
    {
        int status=taskService.updateTaskDeadline(userid,task);
        return returnByStatus(status);
    }

    @PutMapping("/task/description")
    public ResponseEntity<?> updateTaskDescription(@RequestParam(name = "userid") Long userid,@RequestBody Task task)
    {
        int status=taskService.updateTaskDescription(userid,task);
        return returnByStatus(status);
    }


    @PutMapping("/task/title")
    public ResponseEntity<?> updateTaskTitle(@RequestParam(name = "userid") Long userid,@RequestBody Task task)
    {
        int status=taskService.updateTaskTitle(userid,task);
        return returnByStatus(status);
    }


    @PutMapping("/task/status")
    public ResponseEntity<?> updateTaskStatus(@RequestParam(name = "userid") Long userid,@RequestBody UserToTask userToTask)
    {
        int status=taskService.updateTaskStatus(userid,userToTask);
        return returnByStatus(status);
    }

//   ----------------   DELETE TASK ------------------------------------------------------------------------------------------------
    //Delete a task by id
    @DeleteMapping("/task")
    public ResponseEntity<?> deleteTaskById(@RequestParam(name = "userid") Long userid, @RequestBody DeleteTaskRequest deleteTaskRequest)
    {
        int status=taskService.deleteTaskById(userid,deleteTaskRequest.getTaskId());
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body(taskNotFound);
        else if(status==4)
            return ResponseEntity.badRequest().body(noAuthority);
        else
            return ResponseEntity.badRequest().body(new MessageResponse("Some error occurred please try again"));
    }

    @DeleteMapping("/task/user")
    public ResponseEntity<?> deleteUserFromMeeting(@RequestParam(name = "userid") Long userid, @RequestBody DeleteUserFromTask deleteUserFromTask)
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
    @GetMapping("/task/creator")
    public ResponseEntity<?> getTasksCreatedBy(@RequestParam(name = "userid") Long userid)
    {
        return taskService.findTaskByCreatorid(userid);
    }

    @GetMapping("/task/noninvited")
    public ResponseEntity<?> findAllNonInvitedUsers(@RequestParam(name = "userid") Long userid,@RequestParam Long taskId)
    {
        return taskService.findAllNonInvitedUsers(userid,taskId);
    }

    @GetMapping("/task/user")
    public ResponseEntity<?> getAllTasksByUserid(@RequestParam(name = "userid") Long userid)
    {
        return taskService.getAllTasksByUserid(userid);
    }

}
