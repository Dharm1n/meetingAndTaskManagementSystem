package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task,Long> {
    List<Task> findByCreator(Long creator);
    void deleteById(Long id);
    boolean existsById(Long id);

    Optional<Task> findById(Long id);

}
