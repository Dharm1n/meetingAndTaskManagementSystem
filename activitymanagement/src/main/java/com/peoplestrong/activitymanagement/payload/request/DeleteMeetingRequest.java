package com.peoplestrong.activitymanagement.payload.request;

public class DeleteMeetingRequest {
    private Long meetingId;
    private Long creatorId;

    public DeleteMeetingRequest(Long meetingId, Long creatorId) {
        this.meetingId = meetingId;
        this.creatorId = creatorId;
    }

    public DeleteMeetingRequest() {
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
