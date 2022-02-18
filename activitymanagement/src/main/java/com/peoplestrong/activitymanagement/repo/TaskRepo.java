package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.Task;
import com.peoplestrong.activitymanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task,Long> {
    Optional<Task> findById(Long id);
    List<Task> findByCreator(String creator);
    void deleteById(Long id);
}
