package com.peoplestrong.activitymanagement.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity @Table(name = "task") @NoArgsConstructor @AllArgsConstructor
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@Column(name = "title")
	private String title;

	@NotBlank
	@Column(name = "creator")
	private Long creator;

	@NotBlank
	@Column(name = "creation_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime creationTime;

	@NotBlank
	@Column(name = "deadline")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime deadline;

	@Column(name = "description" ,length = 65450, columnDefinition = "text")
	private String description;

	@OneToMany(mappedBy = "task", orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<TaskAssignee> taskAssignees = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<TaskAssignee> getTaskAssignees() {
		return taskAssignees;
	}

	public void setTaskAssignees(Set<TaskAssignee> taskAssignees) {
		this.taskAssignees = taskAssignees;
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
		Task other = (Task) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Task{" +
				"id=" + id +
				", title='" + title + '\'' +
				", creator=" + creator +
				", creationTime=" + creationTime +
				", deadline=" + deadline +
				", description='" + description + '\'' +
				", taskAssignees=" + taskAssignees +
				'}';
	}
}
