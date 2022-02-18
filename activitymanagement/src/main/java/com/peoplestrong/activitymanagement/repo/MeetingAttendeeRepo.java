package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.Meeting;
import com.peoplestrong.activitymanagement.models.MeetingAttendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingAttendeeRepo extends JpaRepository<MeetingAttendee,Long> {
    Optional<Meeting> findByMeetingIdAndUserId(Long meetingid,Long userid);
}
