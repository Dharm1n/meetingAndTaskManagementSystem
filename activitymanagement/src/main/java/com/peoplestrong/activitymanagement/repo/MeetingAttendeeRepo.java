package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.Meeting;
import com.peoplestrong.activitymanagement.models.MeetingAttendee;
import com.peoplestrong.activitymanagement.models.MeetingAttendeeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingAttendeeRepo extends JpaRepository<MeetingAttendee,Long> {
    Optional<MeetingAttendee> findByMeetingIdAndUserId(Long meetingId,Long userId);
    List<MeetingAttendee> findByUserId(Long userid);
    List<MeetingAttendee> findByMeetingId(Long meetingId);

    List<MeetingAttendee> deleteByUserIdAndMeetingId(Long userid,Long meetingid);
}
