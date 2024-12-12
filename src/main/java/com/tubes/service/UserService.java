package com.tubes.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tubes.entity.Reader;
import com.tubes.entity.User;
import com.tubes.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }

    public boolean checkEmailExisting(String email) throws Exception {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean checkUsernameExisting(String username) throws Exception {
        return userRepository.findByUsername(username) != null;
    }

    public void saveReader(String firstName, String lastName, String email, String username, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Reader reader = new Reader();
        reader.setUsername(username);
        reader.setEmail(email);
        reader.setPassword(passwordEncoder.encode(password));
        reader.setFirstName(firstName);
        reader.setLastName(lastName);
        reader.setDateJoined(LocalDateTime.now());
        userRepository.save(reader);
    }
}
