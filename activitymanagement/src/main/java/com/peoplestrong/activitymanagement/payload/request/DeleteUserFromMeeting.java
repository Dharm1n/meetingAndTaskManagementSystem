package com.peoplestrong.activitymanagement.payload.request;

public class DeleteUserFromMeeting {
    private Long meetingId;
    private Long userToBeDeleted;

    public DeleteUserFromMeeting(Long meetingId, Long userToBeDeleted) {
        this.meetingId = meetingId;
        this.userToBeDeleted = userToBeDeleted;
    }

    public DeleteUserFromMeeting() {
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Long getUserToBeDeleted() {
        return userToBeDeleted;
    }

    public void setUserToBeDeleted(Long userToBeDeleted) {
        this.userToBeDeleted = userToBeDeleted;
    }
}
