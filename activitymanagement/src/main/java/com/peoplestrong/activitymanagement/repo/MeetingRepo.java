package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface MeetingRepo extends JpaRepository<Meeting,Long> {
    Optional<Meeting> findById(Long id);
    Set<Meeting> findByCreator(Long creator);
}
