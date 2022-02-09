package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
