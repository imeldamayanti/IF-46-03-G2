package com.tubes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.entity.User;
import com.tubes.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}
