package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.TaskAssignee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskAssigneeRepo extends JpaRepository<TaskAssignee,Long> {
    Optional<String> findByUserIdAndTaskId(Long userId, Long taskId);
}
