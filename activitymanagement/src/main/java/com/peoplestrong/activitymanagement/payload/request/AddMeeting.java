package com.peoplestrong.activitymanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.peoplestrong.activitymanagement.models.MeetingAttendee;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddMeeting {

    private String purpose;
    private Long creator;
    private String place;
    private String startTime;
    private String endTime;
    private String description;
    private List<Long> meetingAttendees = new ArrayList<>();

    public AddMeeting() {
    }

    public AddMeeting(String purpose, Long creator, String place, String startTime, String endTime, String description, List<Long> meetingAttendees) {
        this.purpose = purpose;
        this.creator = creator;
        this.place = place;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.meetingAttendees = meetingAttendees;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getMeetingAttendees() {
        return meetingAttendees;
    }

    public void setMeetingAttendees(List<Long> meetingAttendees) {
        this.meetingAttendees = meetingAttendees;
    }
}
