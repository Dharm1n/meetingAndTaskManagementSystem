package com.peoplestrong.activitymanagement.api;

import com.peoplestrong.activitymanagement.models.Task;
import com.peoplestrong.activitymanagement.models.TaskAssignee;
import com.peoplestrong.activitymanagement.payload.request.UserToTask;
import com.peoplestrong.activitymanagement.repo.TaskRepo;
import com.peoplestrong.activitymanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController @RequiredArgsConstructor @RequestMapping("/api") @Slf4j @PreAuthorize("hasRole('ROLE_USER')")
public class TaskController {

    @Autowired
    TaskRepo taskRepo;

    @Autowired
    TaskService taskService;
//    --------------   CREATE TASK   ------------------------------------------------------------------------------------------------
    //Add a task
    @PostMapping("/task")
    public ResponseEntity<?> addTask(@RequestBody Task task)
    {
        taskRepo.save(task);
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(task);
    }

//   ----------------   UPDATE TASK ------------------------------------------------------------------------------------------------
    //Add a user to task
    @PostMapping("/task/adduser")
    public ResponseEntity<?> addUserToTask(@RequestBody UserToTask userToTask)
    {
        int status=taskService.addUserToTask(userToTask);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body("Error: Task doesn't exist.");
        else if(status==2)
            return ResponseEntity.badRequest().body("Error: User creating the task doesn't exist.");
        else if(status==3)
            return ResponseEntity.badRequest().body("Error: User is already assigned to that task.");
        else
            return ResponseEntity.badRequest().body("Some error occurred please try again");
    }

    @PutMapping("/task")
    public ResponseEntity<?> updateTask(@RequestBody Task task)
    {
        int status=taskService.updateTask(task);
        if(status==0)
        return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body("Error: Task doesn't exist.");
        else if(status==2)
            return ResponseEntity.badRequest().body("Error: User creating the task doesn't exist.");
        else
            return ResponseEntity.badRequest().body("Some error occurred please try again");
    }


    @PutMapping("/task/creator")
    public ResponseEntity<?> updateTaskCreator(@RequestBody Task task)
    {
        int status=taskService.updateTaskCreator(task);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body("Error: Task doesn't exist.");
        else if(status==2)
            return ResponseEntity.badRequest().body("Error: User creating the task doesn't exist.");
        else
            return ResponseEntity.badRequest().body("Some error occurred please try again");
    }


    @PutMapping("/task/deadline")
    public ResponseEntity<?> updateTaskDeadline(@RequestBody Task task)
    {
        int status=taskService.updateTaskDeadline(task);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body("Error: Task doesn't exist.");
        else if(status==2)
            return ResponseEntity.badRequest().body("Error: User creating the task doesn't exist.");
        else
            return ResponseEntity.badRequest().body("Some error occurred please try again");
    }

    @PutMapping("/task/description")
    public ResponseEntity<?> updateTaskDescription(@RequestBody Task task)
    {
        int status=taskService.updateTaskDescription(task);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body("Error: Task doesn't exist.");
        else if(status==2)
            return ResponseEntity.badRequest().body("Error: User creating the task doesn't exist.");
        else
            return ResponseEntity.badRequest().body("Some error occurred please try again");
    }


    @PutMapping("/task/title")
    public ResponseEntity<?> updateTaskTitle(@RequestBody Task task)
    {
        int status=taskService.updateTaskTitle(task);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body("Error: Task doesn't exist.");
        else if(status==2)
            return ResponseEntity.badRequest().body("Error: User creating the task doesn't exist.");
        else
            return ResponseEntity.badRequest().body("Some error occurred please try again");
    }


    @PutMapping("/task/{userid}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable(name = "userid") Long userid,@RequestBody TaskAssignee taskAssignee)
    {
        int status=taskService.updateTaskStatus(userid,taskAssignee);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body("Error: Task doesn't exist.");
        else if(status==2)
            return ResponseEntity.badRequest().body("Error: User creating the task doesn't exist.");
        else
            return ResponseEntity.badRequest().body("Some error occurred please try again");
    }
//   ----------------   DELETE TASK ------------------------------------------------------------------------------------------------
    //Delete a task by id
    @DeleteMapping("/task/{taskid}")
    public ResponseEntity<?> deleteTaskById(@PathVariable(name="taskid") Long taskid)
    {
        int status=taskService.deleteTaskById(taskid);
        if(status==0)
            return ResponseEntity.ok().build();
        else if(status==1)
            return ResponseEntity.badRequest().body("Error: Task doesn't exist.");
        else
            return ResponseEntity.badRequest().body("Some error occurred please try again");
    }


//   ----------------   READ TASK ------------------------------------------------------------------------------------------------
    @GetMapping("/task/creator/{userid}")
    public ResponseEntity<?> getTasksCreatedBy(@PathVariable(name = "userid") Long userid)
    {
        return taskService.findTaskByCreatorid(userid);
    }

    @GetMapping("/task/user/{userid}")
    public ResponseEntity<?> getAllTasksByUserid(@PathVariable(name = "userid") Long userid)
    {
        return taskService.getAllTasksByUserid(userid);
    }

}
