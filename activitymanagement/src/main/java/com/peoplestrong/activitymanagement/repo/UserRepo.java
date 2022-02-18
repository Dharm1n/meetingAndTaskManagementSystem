package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    List<User> findByUsernameEndsWith(String username);
}
