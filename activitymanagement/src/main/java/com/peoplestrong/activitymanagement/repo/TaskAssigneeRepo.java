package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.Task;
import com.peoplestrong.activitymanagement.models.TaskAssignee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAssigneeRepo extends JpaRepository<TaskAssignee,Long> {
    Optional<TaskAssignee> findByUserIdAndTaskId(Long userId, Long taskId);
    List<TaskAssignee> findByUserId(Long usreId);

    List<TaskAssignee> deleteByUserIdAndTaskId(Long userId,Long taskId);
}
