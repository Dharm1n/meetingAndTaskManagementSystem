package com.peoplestrong.activitymanagement.repo;

import com.peoplestrong.activitymanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByResetToken(String resetToken);

    boolean existsByUsername(String username);
    boolean existsById(Long id);
    List<User> findByUsernameEndsWith(String username);

}
