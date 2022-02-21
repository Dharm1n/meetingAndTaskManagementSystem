package com.peoplestrong.activitymanagement.models;

import javax.persistence.*;

@Entity
@Table(name = "meeting_attendee")
public class MeetingAttendee {
    @EmbeddedId
    private MeetingAttendeeKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("meetingId")
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column(name = "status")
    private String status;

    public MeetingAttendee(MeetingAttendeeKey id, User user, Meeting meeting, String status) {
        this.id = id;
        this.user = user;
        this.meeting = meeting;
        this.status = status;
    }

    public MeetingAttendee() {
    }

    public MeetingAttendeeKey getId() {
        return id;
    }

    public void setId(MeetingAttendeeKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MeetingAttendee other = (MeetingAttendee) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
