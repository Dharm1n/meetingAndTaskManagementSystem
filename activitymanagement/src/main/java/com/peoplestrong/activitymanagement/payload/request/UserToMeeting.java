package com.peoplestrong.activitymanagement.payload.request;

import java.util.List;

public class UserToMeeting {
    private Long userId;
    private Long meetingId;
    private String status;

    public UserToMeeting(Long userId, Long meetingId, String status) {
        this.userId = userId;
        this.meetingId = meetingId;
        this.status = status;
    }

    public UserToMeeting() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
