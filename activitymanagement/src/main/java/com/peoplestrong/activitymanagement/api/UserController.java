package com.peoplestrong.activitymanagement.api;

import com.peoplestrong.activitymanagement.models.Role;
import com.peoplestrong.activitymanagement.models.User;
import com.peoplestrong.activitymanagement.payload.response.IdResponse;
import com.peoplestrong.activitymanagement.payload.response.MessageResponse;
import com.peoplestrong.activitymanagement.payload.response.UserNameEmail;
import com.peoplestrong.activitymanagement.repo.RoleRepo;
import com.peoplestrong.activitymanagement.repo.UserRepo;
import com.peoplestrong.activitymanagement.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController @RequiredArgsConstructor @RequestMapping("/api") @Slf4j @PreAuthorize("hasRole('ROLE_USER')")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users/{username}")
    public ResponseEntity<?> getUsers(@PathVariable("username") String username) {
        log.error("{}",username);
        if(!userRepo.existsByUsername(username))
        {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid email1."));
        }
        int orgStartIndex=username.indexOf('@');
        if(orgStartIndex==-1)
        {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid email."));
        }

        int dotIndex=(username.split("@")[1]).indexOf('.');
        if(dotIndex==-1)
        {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid email."));
        }
        String orgName=username.substring(orgStartIndex);
        log.error(orgName);

        List<User> userList=userRepo.findByUsernameEndsWith(orgName);
        List<UserNameEmail> userNameEmails=new ArrayList<>();
        userList.forEach(user -> {
            userNameEmails.add(new UserNameEmail(user.getId(), user.getUsername(),user.getName()));
        });
        return ResponseEntity.ok().body(userNameEmails);
    }

    @PostMapping("/users/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        if(userRepo.existsByUsername(user.getUsername()))
        {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User already exists with email:"+ user.getUsername()));
        }
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

        user.getRoles().clear();
        Collection<Role> roles=new ArrayList<>();
        Role userRole=roleRepo.findByName("ROLE_USER").orElseThrow(() ->new RuntimeException("Role Doesn't exist"));
        roles.add(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepo.save(user);
        return ResponseEntity.created(uri).body(new IdResponse(user.getId()));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserIdByUsername(@PathVariable(name = "username") String username)
    {
        return userService.getUserIdByUsername(username);
    }
//    @PostMapping("/role/save")
//    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
//        //log.error("\n\nin api");
//        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
//        return ResponseEntity.created(uri).body(userService.saveRole(role));
//    }

//    @PostMapping("/role/addtouser")
//    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
//        userService.addRoleToUser(form.getUsername(),form.getRoleName());
//        return ResponseEntity.ok().build();
//    }

}


