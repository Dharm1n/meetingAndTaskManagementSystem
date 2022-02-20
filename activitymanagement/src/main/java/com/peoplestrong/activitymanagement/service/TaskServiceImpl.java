package com.peoplestrong.activitymanagement.service;

import com.peoplestrong.activitymanagement.models.Task;
import com.peoplestrong.activitymanagement.models.TaskAssignee;
import com.peoplestrong.activitymanagement.models.TaskAssigneeKey;
import com.peoplestrong.activitymanagement.models.User;
import com.peoplestrong.activitymanagement.payload.request.UserToTask;
import com.peoplestrong.activitymanagement.payload.response.TaskFromTaskassignee;
import com.peoplestrong.activitymanagement.repo.TaskAssigneeRepo;
import com.peoplestrong.activitymanagement.repo.TaskRepo;
import com.peoplestrong.activitymanagement.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class TaskServiceImpl implements TaskService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskAssigneeRepo taskAssigneeRepo;

    @Override
    public int addUserToTask(UserToTask userToTask) {
        Optional<TaskAssignee> taskAssignee=taskAssigneeRepo.findByUserIdAndTaskId(userToTask.getUserId(),userToTask.getTaskId());
        Optional<User> user=userRepo.findById(userToTask.getUserId());
        Optional<Task> task=taskRepo.findById(userToTask.getTaskId());
        if(taskAssignee.isPresent())
        {
            log.error("User is already assigned to task");
            return 3;
        }
        if(!user.isPresent())
        {
            log.error("User not found in db");
            //throw new UsernameNotFoundException("User does not exist.");
            return 2;
        }
        if(!task.isPresent())
        {
            log.error("Task doesn't exist.");
            //throw new UsernameNotFoundException("Task doesn't exist.");
            return 1;
        }
        log.error("{}",taskRepo.findById(userToTask.getTaskId()).get().toString());
        //Option 2
        //user.get().getTaskStatus().add(new TaskAssignee(null,user.get(),task.get(),userToTask.getStatus()));
        //userRepo.save(user.get());

        //Option 1
        try{
            taskAssigneeRepo.save(
                    new TaskAssignee(new TaskAssigneeKey(user.get().getId(),task.get().getId()),
                            user.get(),
                            task.get(),
                            userToTask.getStatus()
                    ));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        return 0;

    }

    // Can't update creation time.
    @Override
    public int updateTask(Task task) {
        Optional<Task> taskfromdb=taskRepo.findById(task.getId());

        if(!taskfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(task.getCreator()))
        {
            return 2;
        }
//        taskfromdb.get().setId(task.getId());
        taskfromdb.get().setCreator(task.getCreator());
        taskfromdb.get().setDeadline(task.getDeadline());
        taskfromdb.get().setDescription(task.getDescription());
        taskfromdb.get().setTitle(task.getTitle());

        taskfromdb.get().getTaskAssignees().clear();
        taskfromdb.get().getTaskAssignees().addAll(task.getTaskAssignees());
        taskRepo.save(taskfromdb.get());

        return 0;
    }

    @Override
    public int updateTaskCreator(Task task) {
        Optional<Task> taskfromdb=taskRepo.findById(task.getId());
        if(!taskfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(task.getCreator()))
        {
            return 2;
        }
        taskfromdb.get().setCreator(task.getCreator());
        taskRepo.save(taskfromdb.get());

        return 0;
    }

    @Override
    public int updateTaskDeadline(Task task) {
        Optional<Task> taskfromdb=taskRepo.findById(task.getId());
        if(!taskfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(task.getCreator()))
        {
            return 2;
        }
        taskfromdb.get().setDeadline(task.getDeadline());
        taskRepo.save(taskfromdb.get());
        return 0;
    }

    @Override
    public int updateTaskTitle(Task task) {
        Optional<Task> taskfromdb=taskRepo.findById(task.getId());
        if(!taskfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(task.getCreator()))
        {
            return 2;
        }
        taskfromdb.get().setTitle(task.getTitle());
        taskRepo.save(taskfromdb.get());
        return 0;
    }

    @Override
    public int updateTaskDescription(Task task) {
        Optional<Task> taskfromdb=taskRepo.findById(task.getId());
        if(!taskfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(task.getCreator()))
        {
            return 2;
        }
        taskfromdb.get().setDescription(task.getDescription());
        taskRepo.save(taskfromdb.get());
        return 0;
    }

    @Override
    public int deleteTaskById(Long taskid) {
        Optional<Task> task=taskRepo.findById(taskid);
        if(!task.isPresent())
        {
            return 1;
        }
        taskRepo.deleteById(taskid);
        return 0;
    }

    @Override
    public ResponseEntity<?> findTaskByCreatorid(Long userid) {
        if(!userRepo.existsById(userid))
        {
            return ResponseEntity.badRequest().body("Error: User doesn't exist.");
        }
        List<Task> tasksCreatedbyuserList = taskRepo.findByCreator(userid);
        Set<TaskFromTaskassignee> tasks=new HashSet<>();
        for(Task tasksCreatedbyuser:tasksCreatedbyuserList)
        {
            tasks.add(new TaskFromTaskassignee(userid,
                    tasksCreatedbyuser.getId(),
                    tasksCreatedbyuser.getTitle(),
                    "",
                    tasksCreatedbyuser.getCreator(),
                    tasksCreatedbyuser.getCreationTime(),
                    tasksCreatedbyuser.getDeadline(),
                    tasksCreatedbyuser.getDescription()
            ));
        }
        return ResponseEntity.ok().body(tasks);
    }

    @Override
    public ResponseEntity<?> getAllTasksByUserid(Long userid) {
        Optional<User> user=userRepo.findById(userid);
        if(!user.isPresent())
        {
            return ResponseEntity.badRequest().body("Error: User doesn't exist.");
        }
//
//        Set<TaskAssignee> taskAssigneeSet=user.get().getTaskStatus();
//        List<Task> tasks=new ArrayList<>();
//        for(TaskAssignee taskAssignee:taskAssigneeSet)
//        {
//            tasks.add(taskAssignee.getTask());
//        }
//        return ResponseEntity.ok().body(tasks);
        //Set<TaskAssignee> taskAssigneeSet=user.get().getTaskStatus();

        List<TaskAssignee> taskAssigneeSet=taskAssigneeRepo.findByUserId(userid);
        Set<TaskFromTaskassignee> tasks=new HashSet<>();
        for(TaskAssignee taskAssignee:taskAssigneeSet)
        {
            Task task=taskAssignee.getTask();
            tasks.add(new TaskFromTaskassignee(userid,
                    task.getId(),
                    task.getTitle(),
                    taskAssignee.getStatus(),
                    task.getCreator(),
                    task.getCreationTime(),
                    task.getDeadline(),
                    task.getDescription()
            ));
        }
        List<Task> tasksCreatedbyuserList = taskRepo.findByCreator(userid);
        //Status =="" then don't show
        for(Task tasksCreatedbyuser:tasksCreatedbyuserList)
        {
            tasks.add(new TaskFromTaskassignee(userid,
                    tasksCreatedbyuser.getId(),
                    tasksCreatedbyuser.getTitle(),
                    //Either query or load the whole table
                    "",
                    tasksCreatedbyuser.getCreator(),
                    tasksCreatedbyuser.getCreationTime(),
                    tasksCreatedbyuser.getDeadline(),
                    tasksCreatedbyuser.getDescription()
                    ));
        }
        return ResponseEntity.ok().body(tasks);
    }

    @Override
    public int updateTaskStatus(Long userid, TaskAssignee taskAssignee) {
        Optional<Task> taskfromdb=taskRepo.findById(taskAssignee.getTask().getId());
        if(!taskfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(userid))
        {
            return 2;
        }
        Optional<TaskAssignee> taskAssigneefromdb=taskAssigneeRepo.findByUserIdAndTaskId(
                userid,
                taskfromdb.get().getId());
        taskAssigneefromdb.get().setStatus(taskAssignee.getStatus());
        taskAssigneeRepo.save(taskAssigneefromdb.get());
        return 0;
    }
}
