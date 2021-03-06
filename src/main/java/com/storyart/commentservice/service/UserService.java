package com.storyart.commentservice.service;

import com.storyart.commentservice.model.User;
import com.storyart.commentservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService {
    User findById(Integer id);

    User findByUsername(String username);

}

@Service
class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}