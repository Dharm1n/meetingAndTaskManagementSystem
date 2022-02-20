package com.peoplestrong.activitymanagement.service;


import com.peoplestrong.activitymanagement.models.Role;
import com.peoplestrong.activitymanagement.models.User;
import com.peoplestrong.activitymanagement.repo.RoleRepo;
import com.peoplestrong.activitymanagement.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepo.findByUsername(username);
        if(!user.isPresent())
        {
            log.error("User not found in db");
            throw new UsernameNotFoundException("User not found in db");
        }
        else{
            log.error("User found in db");
        }
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.get().getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),user.get().getPassword(),authorities);
    }
    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to db",user.getName());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to db",role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        Optional<User> user=userRepo.findByUsername(username);
        Optional<Role> role=roleRepo.findByName(rolename);
        if(role.isPresent() && user.isPresent())
        {
            user.get().getRoles().add(role.get());
        }
        else if(!role.isPresent() && user.isPresent())
        {
            throw new RuntimeException("Please provide valid role and username");
        }
        else if(!role.isPresent())
        {
            throw new RuntimeException("Please provide valid role");
        }
        else
        {
            throw new RuntimeException("Please provide valid email");
        }
    }

    @Override
    public Optional<User> getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }
}
