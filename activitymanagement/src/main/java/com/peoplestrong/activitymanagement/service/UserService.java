package com.peoplestrong.activitymanagement.service;

import com.peoplestrong.activitymanagement.models.Role;
import com.peoplestrong.activitymanagement.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String rolename);
    Optional<User> getUser(String username);
    List<User> getUsers();

    ResponseEntity<?> getUserIdByUsername(String username);
}
