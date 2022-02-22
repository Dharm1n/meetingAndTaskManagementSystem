package com.peoplestrong.activitymanagement.service;

import com.peoplestrong.activitymanagement.models.Meeting;
import com.peoplestrong.activitymanagement.models.MeetingAttendee;
import com.peoplestrong.activitymanagement.payload.request.DeleteUserFromMeeting;
import com.peoplestrong.activitymanagement.payload.request.UserToMeeting;
import org.springframework.http.ResponseEntity;

public interface MeetingService {
    int addUserToMeeting(UserToMeeting userToMeeting);

    int updateMeeting(Meeting meeting);

    int updateMeetingTime(Meeting meeting);

    int updateMeetingDescription(Meeting meeting);

    int updateMeetingPurpose(Meeting meeting);

    int updateMeetingPlace(Meeting meeting);

    int updateMeetingStatus(Long userid, UserToMeeting userToMeeting);

    int deleteMeetingById(Long meetingId,Long userid);

    ResponseEntity<?> findMeetingByCreatorid(Long userid);

    ResponseEntity<?> getAllMeetingsByUserid(Long userid);

    int deleteUserFromMeeting(DeleteUserFromMeeting deleteUserFromMeeting, Long userid);

    ResponseEntity<?> findAllNonInvitedUsers(Long userId, Long meetingId);
}
