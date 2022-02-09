package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
