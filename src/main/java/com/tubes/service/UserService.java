package com.tubes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tubes.entity.User;
import com.tubes.repository.UserRepository;

@Service
// public class UserService implements UserDetailsService {
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     User user = userRepository.findByUsername(username);
    //     if (user == null) {
    //         throw new UsernameNotFoundException("User not found");
    //     }

    //     return new org.springframework.security.core.userdetails.User(
    //         user.getUsername(),
    //         user.getPassword(),
    //         List.of(new SimpleGrantedAuthority(user.getRole()))
    //     );
    // }
}
