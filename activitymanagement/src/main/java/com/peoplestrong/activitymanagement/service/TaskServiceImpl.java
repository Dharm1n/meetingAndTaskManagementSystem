package com.peoplestrong.activitymanagement.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.peoplestrong.activitymanagement.models.*;
import com.peoplestrong.activitymanagement.payload.request.DeleteUserFromTask;
import com.peoplestrong.activitymanagement.payload.request.UserToTask;
import com.peoplestrong.activitymanagement.payload.response.*;
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
import java.util.concurrent.atomic.AtomicBoolean;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class TaskServiceImpl implements TaskService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskAssigneeRepo taskAssigneeRepo;

    @Autowired
    private UserNotFound userNotFound;

    @Autowired
    TaskNotFound taskNotFound;

    @Autowired
    NoAuthority noAuthority;

    @Override
    public int addUserToTask(Long userid,UserToTask userToTask) {
        Optional<TaskAssignee> taskAssignee=taskAssigneeRepo.findByUserIdAndTaskId(userToTask.getUserId(),userToTask.getTaskId());
        Optional<User> user=userRepo.findById(userToTask.getUserId());
        Optional<Task> task=taskRepo.findById(userToTask.getTaskId());
        if(taskAssignee.isPresent())
        {
            log.error("User is already assigned to task");
            return 3;
        }
        if(!user.isPresent() || !userRepo.existsById(userid))
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
        if(!Objects.equals(userid, task.get().getCreator()))
            return 4;
        log.error("{}",taskRepo.findById(userToTask.getTaskId()).get().toString());

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

    @Override
    public int updateTask(Long userid,Task task) {
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
        taskfromdb.get().setDeadline(task.getDeadline());
        taskfromdb.get().setDescription(task.getDescription());
        taskfromdb.get().setTitle(task.getTitle());

        taskRepo.save(taskfromdb.get());

        return 0;
    }

    @Override
    public int updateTaskCreator(Long userid,Task task) {
        Optional<Task> taskfromdb=taskRepo.findById(task.getId());
        if(!taskfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(task.getCreator()))
        {
            return 2;
        }
        if(!Objects.equals(taskfromdb.get().getCreator(), userid))
            return 4;
        taskfromdb.get().setCreator(task.getCreator());
        taskRepo.save(taskfromdb.get());

        return 0;
    }

    @Override
    public int updateTaskDeadline(Long userid,Task task) {
        Optional<Task> taskfromdb=taskRepo.findById(task.getId());
        if(!taskfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(task.getCreator()))
        {
            return 2;
        }
        if(!Objects.equals(taskfromdb.get().getCreator(), userid))
            return 4;
        taskfromdb.get().setDeadline(task.getDeadline());
        taskRepo.save(taskfromdb.get());
        return 0;
    }

    @Override
    public int updateTaskTitle(Long userid,Task task) {
        Optional<Task> taskfromdb=taskRepo.findById(task.getId());
        if(!taskfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(task.getCreator()))
        {
            return 2;
        }
        if(!Objects.equals(taskfromdb.get().getCreator(), userid))
            return 4;
        taskfromdb.get().setTitle(task.getTitle());
        taskRepo.save(taskfromdb.get());
        return 0;
    }

    @Override
    public int updateTaskDescription(Long userid,Task task) {
        Optional<Task> taskfromdb=taskRepo.findById(task.getId());
        if(!taskfromdb.isPresent())
        {
            return 1;
        }
        if(!userRepo.existsById(task.getCreator()))
        {
            return 2;
        }
        if(!Objects.equals(taskfromdb.get().getCreator(), userid))
            return 4;
        taskfromdb.get().setDescription(task.getDescription());
        taskRepo.save(taskfromdb.get());
        return 0;
    }

    @Override
    public int deleteTaskById(Long userid,Long taskid) {
        Optional<Task> task=taskRepo.findById(taskid);
        if(!task.isPresent())
        {
            return 1;
        }
        if(!Objects.equals(task.get().getCreator(), userid))
            return 4;
        taskRepo.deleteById(taskid);
        return 0;
    }


    @Override
    public int deleteUserFromTask(DeleteUserFromTask deleteUserFromTask, Long userid) {
        Optional<Task> task=taskRepo.findById(deleteUserFromTask.getTaskId());
        if(!task.isPresent())
        {
            return 1;
        }

        if(!Objects.equals(task.get().getCreator(), userid))
            return 2;
        if(!userRepo.existsById(deleteUserFromTask.getUserToBeDeleted()))
        {
            return 3;
        }

        Optional<TaskAssignee> taskAssignee=taskAssigneeRepo.findByUserIdAndTaskId(deleteUserFromTask.getUserToBeDeleted(), deleteUserFromTask.getTaskId());

        if(!taskAssignee.isPresent())
            return 4;
        try{
            taskAssigneeRepo.deleteByUserIdAndTaskId(deleteUserFromTask.getUserToBeDeleted(),deleteUserFromTask.getTaskId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    @Override
    public ResponseEntity<?> findTaskByCreatorid(Long userid) {
        Optional<User> user=userRepo.findById(userid);
        if(!user.isPresent())
        {
            return ResponseEntity.badRequest().body(userNotFound);
        }
        List<Task> tasksCreatedbyuserList = taskRepo.findByCreator(userid);
        List<TaskFromCreator> taskCreated=new ArrayList<>();

        for(Task tasksCreatedbyuser:tasksCreatedbyuserList)
        {
            AtomicBoolean taskCompleted= new AtomicBoolean(true);
            List<UserTaskStatus> userTaskStatuses=new ArrayList<>();
            tasksCreatedbyuser.getTaskAssignees().forEach(taskAssignee1 -> {
                userTaskStatuses.add(new UserTaskStatus(taskAssignee1.getUser().getUsername(),
                                taskAssignee1.getUser().getName(),
                                taskAssignee1.getStatus()
                        )
                );
                if(!taskAssignee1.getStatus().equals("Done"))
                {
                    taskCompleted.set(false);
                }
            });
            String status;
            if(taskCompleted.get())
            {
                status="Done";
            }
            else
            {
                status="In progress";
            }
            taskCreated.add(new TaskFromCreator(userid,
                    tasksCreatedbyuser.getId(),
                    tasksCreatedbyuser.getTitle(),
                    status,
                    tasksCreatedbyuser.getCreator(),
                    tasksCreatedbyuser.getCreationTime(),
                    tasksCreatedbyuser.getDeadline(),
                    tasksCreatedbyuser.getDescription(),
                    userTaskStatuses,
                    user.get().getName()
            ));
        }
        taskCreated.sort((o1, o2) -> o1.getDeadline().compareTo(o2.getDeadline()));
        return ResponseEntity.ok().body(taskCreated);
    }

    @Override
    public ResponseEntity<?> getAllTasksByUserid(Long userid) {
        Optional<User> user=userRepo.findById(userid);
        if(!user.isPresent())
        {
            return ResponseEntity.badRequest().body(userNotFound);
        }

        List<TaskAssignee> taskAssigneeSet=taskAssigneeRepo.findByUserId(userid);
        List<TaskFromTaskassignee> taskAssigned=new ArrayList<>();
        for(TaskAssignee taskAssignee:taskAssigneeSet)
        {
            Task task=taskAssignee.getTask();
            taskAssigned.add(new TaskFromTaskassignee(userid,
                    task.getId(),
                    task.getTitle(),
                    taskAssignee.getStatus(),
                    task.getCreator(),
                    task.getCreationTime(),
                    task.getDeadline(),
                    task.getDescription(),
                    userRepo.findById(task.getCreator()).get().getName()
            ));
        }
        taskAssigned.sort((o1, o2) -> o1.getDeadline().compareTo(o2.getDeadline()));

        List<TaskFromCreator> taskCreated=new ArrayList<>();
        List<Task> tasksCreatedbyuserList = taskRepo.findByCreator(userid);

        for(Task tasksCreatedbyuser:tasksCreatedbyuserList)
        {
            AtomicBoolean taskCompleted= new AtomicBoolean(true);
            List<UserTaskStatus> userTaskStatuses=new ArrayList<>();
            tasksCreatedbyuser.getTaskAssignees().forEach(taskAssignee1 -> {
                userTaskStatuses.add(new UserTaskStatus(taskAssignee1.getUser().getUsername(),
                                taskAssignee1.getUser().getName(),
                                taskAssignee1.getStatus()
                        )
                );
                if(!taskAssignee1.getStatus().equals("Done"))
                {
                    taskCompleted.set(false);
                }
            });
            String status;
            if(taskCompleted.get())
            {
                status="Done";
            }
            else
            {
                status="In progress";
            }
            taskCreated.add(new TaskFromCreator(userid,
                    tasksCreatedbyuser.getId(),
                    tasksCreatedbyuser.getTitle(),
                    status,
                    tasksCreatedbyuser.getCreator(),
                    tasksCreatedbyuser.getCreationTime(),
                    tasksCreatedbyuser.getDeadline(),
                    tasksCreatedbyuser.getDescription(),
                    userTaskStatuses,
                    user.get().getName()
            ));
        }
        taskCreated.sort((o1, o2) -> o1.getDeadline().compareTo(o2.getDeadline()));
        Map<String,Object> allTask=new HashMap<String,Object>();
        allTask.put("created",taskCreated);
        allTask.put("assigned",taskAssigned);
        return ResponseEntity.ok().body(allTask);
    }

    @Override
    public int updateTaskStatus(Long userid, UserToTask userToTask) {
        Optional<Task> taskfromdb=taskRepo.findById(userToTask.getTaskId());
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
        if(taskAssigneefromdb.isPresent())
        {
            taskAssigneefromdb.get().setStatus(userToTask.getStatus());
            taskAssigneeRepo.save(taskAssigneefromdb.get());
            return 0;
        }
        else
            return 3;
    }

    @Override
    public ResponseEntity<?> findAllNonInvitedUsers(Long userId, Long taskId) {
        Optional<Task> taskfromdb=taskRepo.findById(taskId);
        if(!taskfromdb.isPresent())
        {
            return ResponseEntity.badRequest().body(taskNotFound);
        }

        Optional<User> userfromdb=userRepo.findById(userId);
        if(!userfromdb.isPresent())
        {
            return ResponseEntity.badRequest().body(userNotFound);
        }

        if(!Objects.equals(taskfromdb.get().getCreator(), userId))
        {
            return ResponseEntity.badRequest().body(noAuthority);
        }

        String username=userfromdb.get().getUsername();
        int orgStartIndex=username.indexOf('@');

        String orgName=username.substring(orgStartIndex);

        List<User> usersfromdb=userRepo.findByUsernameEndsWith(orgName);

        List<UserNameEmail> userNameEmailsList=new ArrayList<>();

        usersfromdb.forEach(user -> {
            if(!taskAssigneeRepo.findByUserIdAndTaskId(user.getId(),taskId).isPresent())
            {
                userNameEmailsList.add(new UserNameEmail(user.getId(), user.getUsername(),user.getName()));
            }
        });
        return ResponseEntity.ok().body(userNameEmailsList);
    }
}
