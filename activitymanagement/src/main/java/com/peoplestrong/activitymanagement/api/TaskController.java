package com.peoplestrong.activitymanagement.api;

import com.peoplestrong.activitymanagement.models.Task;
import com.peoplestrong.activitymanagement.repo.TaskRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController @RequiredArgsConstructor @RequestMapping("/api") @Slf4j
public class TaskController {

    @Autowired
    TaskRepo taskRepo;

    @PostMapping("/task")
    public ResponseEntity<?> addTask(@RequestBody Task task)
    {
        taskRepo.save(task);
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(task);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> addTask(@RequestBody Long id)
    {
        taskRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
