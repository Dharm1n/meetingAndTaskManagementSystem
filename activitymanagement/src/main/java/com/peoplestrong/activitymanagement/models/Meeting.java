package com.peoplestrong.activitymanagement.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "meeting") @NoArgsConstructor @AllArgsConstructor
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@Column(name = "purpose")
	private String purpose;

	@NotBlank
	@Column(name = "creator")
	private Long creator;

	@NotBlank
	@Column(name = "place")
	private String place;

	@NotBlank
	@Column(name = "creation_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime creationTime;

	@NotBlank
	@Column(name = "meeting_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime meetingTime;

	@Column(name = "description" ,length = 65450, columnDefinition = "text")
	private String description;

	@OneToMany(mappedBy = "meeting", orphanRemoval = true,fetch = FetchType.LAZY)
	private Set<MeetingAttendee> meetingAttendees = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<MeetingAttendee> getMeetingAttendees() {
		return meetingAttendees;
	}

	public void setMeetingAttendees(Set<MeetingAttendee> meetingAttendees) {
		this.meetingAttendees = meetingAttendees;
	}

	public Meeting(Long id, String purpose, Long creator, String place, LocalDateTime creationTime, LocalDateTime meetingTime, String description) {
		this.id = id;
		this.purpose = purpose;
		this.creator = creator;
		this.place = place;
		this.creationTime = creationTime;
		this.meetingTime = meetingTime;
		this.description = description;
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
		Meeting other = (Meeting) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
