package com.peoplestrong.activitymanagement.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MeetingAttendeeKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "meeting_id")
    private Long meetingId;

    public MeetingAttendeeKey() {
    }

    public MeetingAttendeeKey(Long userId, Long meetingId) {
        this.userId = userId;
        this.meetingId = meetingId;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((meetingId == null) ? 0 : meetingId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
        MeetingAttendeeKey other = (MeetingAttendeeKey) obj;
        if (meetingId == null) {
            if (other.meetingId != null)
                return false;
        } else if (!meetingId.equals(other.meetingId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }
}
