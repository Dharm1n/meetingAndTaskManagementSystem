package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MeetingRepo extends JpaRepository<Meeting,Long> {
    Optional<Meeting> findById(Long id);
    List<Meeting> findByCreator(Long creator);


}
