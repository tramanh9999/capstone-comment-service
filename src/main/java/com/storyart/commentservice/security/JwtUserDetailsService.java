package com.storyart.commentservice.security;

import com.storyart.commentservice.model.User;
import com.storyart.commentservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userService.findByUsername(username);
        if (byUsername != null) {
            return UserPrincipal.create(byUsername);
        } else {
            throw new UsernameNotFoundException("Username not found with username:" + username);
        }
    }

    public UserDetails loadUserById(Integer id) {
        User user = userService.findById(id);
        if (user != null) {
            return UserPrincipal.create(user);
        } else {
            throw new UsernameNotFoundException("Username not found with userId: " + id);
        }
    }
}
