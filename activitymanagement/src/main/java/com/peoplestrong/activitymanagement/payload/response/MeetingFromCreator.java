package com.peoplestrong.activitymanagement.payload.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingFromCreator {
    private Long meetingid;
    private String purpose;
    private Long creator;
    private LocalDateTime creationTime;
    private LocalDateTime meetingTime;
    private String description;
    private List<UserMeetingStatus> meetingAttendees=new ArrayList<>();
    private Long accepted;
    private Long rejected;
    private String creatorName;

    public MeetingFromCreator(Long meetingid, String purpose, Long creator, LocalDateTime creationTime, LocalDateTime meetingTime, String description, List<UserMeetingStatus> meetingAttendees, Long accepted, Long rejected, String creatorName) {
        this.meetingid = meetingid;
        this.purpose = purpose;
        this.creator = creator;
        this.creationTime = creationTime;
        this.meetingTime = meetingTime;
        this.description = description;
        this.meetingAttendees = meetingAttendees;
        this.accepted = accepted;
        this.rejected = rejected;
        this.creatorName = creatorName;
    }

    public Long getAccepted() {
        return accepted;
    }

    public void setAccepted(Long accepted) {
        this.accepted = accepted;
    }

    public Long getRejected() {
        return rejected;
    }

    public void setRejected(Long rejected) {
        this.rejected = rejected;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public MeetingFromCreator() {
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

    public List<UserMeetingStatus> getMeetingAttendees() {
        return meetingAttendees;
    }

    public void setMeetingAttendees(List<UserMeetingStatus> meetingAttendees) {
        this.meetingAttendees = meetingAttendees;
    }
}
