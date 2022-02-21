package com.peoplestrong.activitymanagement.payload.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingFromMeetingAttendee {
    private Long meetingid;
    private String purpose;
    private Long creator;
    private LocalDateTime creationTime;
    private LocalDateTime meetingTime;
    private String description;
    private String status;

    public MeetingFromMeetingAttendee(Long meetingid, String purpose, Long creator, LocalDateTime creationTime, LocalDateTime meetingTime, String description,String status) {
        this.meetingid = meetingid;
        this.purpose = purpose;
        this.creator = creator;
        this.creationTime = creationTime;
        this.meetingTime = meetingTime;
        this.description = description;
        this.status = status;
    }

    public MeetingFromMeetingAttendee() {
    }

    public Long getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(Long meetingid) {
        this.meetingid = meetingid;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(LocalDateTime meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
