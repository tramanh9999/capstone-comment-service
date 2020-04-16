package com.storyart.commentservice.service;

import com.storyart.commentservice.model.Role;
import com.storyart.commentservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface RoleService {
    Role findRoleById(int id);
}

@Service
class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findRoleById(int id) {
        return roleRepository.findById(id).orElse(null);
    }
}
