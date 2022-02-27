package com.peoplestrong.activitymanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.persistence.*;

@Entity @NoArgsConstructor @AllArgsConstructor
public class User {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@NotBlank
	@Column(name = "email",unique=true)
	private String username;

	@NotBlank
	@Column(name="password")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Role> roles = new ArrayList();

	@OneToMany(mappedBy = "user", orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<TaskAssignee> taskStatus = new HashSet<>();

	@OneToMany(mappedBy = "user", orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<MeetingAttendee> meetingStatus = new HashSet<>();

	@Column(name="reset_token")
	private String resetToken;

	public Set<MeetingAttendee> getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(Set<MeetingAttendee> meetingStatus) {
		this.meetingStatus = meetingStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Set<TaskAssignee> getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Set<TaskAssignee> taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getResetToken()
	{
		return resetToken;
	}

	public void setResetToken(String resetToken)
	{
		this.resetToken=resetToken;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getName() {
		StringBuilder fullName = new StringBuilder();
		fullName.append(firstName);
		fullName.append(" ");
		fullName.append(middleName);
		fullName.append(" ");
		fullName.append(lastName);
		return fullName.toString();
	}
}
