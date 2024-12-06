package com.tubes.seeder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Component;

import com.opencsv.exceptions.CsvException;
import com.tubes.repository.UserRepository;
import com.tubes.utils.csvUtils;

@Component
public class UserSeeder {
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void seedUsers() throws CsvException{
        userRepository.deleteAll();

        try{
            List<String[]> rows = csvUtils.readCsv("seeds/users.csv");
            List<User> users = new ArrayList<>();

            for(String[] row : rows){
                User user = new User();
                user.se
                
            }
        }catch(IOException e){
            e.printStackTrace();

        }

    }
}
