package com.tubes.seeder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opencsv.exceptions.CsvException;
import com.tubes.entity.Admin;
import com.tubes.entity.Reader;
import com.tubes.entity.User;
import com.tubes.repository.UserRepository;
import com.tubes.utils.csvUtils;

@Component
public class UserSeeder {
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void seedUsers() throws CsvException{
        userRepository.deleteAll();
            // userRepository.resetAutoIncrement();
            // userRepository.disableForeignKeyChecks();
            // userRepository.truncateUserTable();
            // userRepository.enableForeignKeyChecks();
        try{
            List<String[]> rows = csvUtils.readCsv("seeds/users.csv");
            List<User> users = new ArrayList<>();

            for(String[] row : rows){
                if(row[7].equals("admin")){
                    Admin admin = new Admin();
                    admin.setId(Long.parseLong(row[0]));
                    admin.setUsername(row[1]);
                    admin.setEmail(row[2]);
                    admin.setPassword(row[3]);
                    admin.setFirstName(row[4]);
                    admin.setLastName(row[5]);
                    admin.setDateJoined(row[6]);;
                    users.add(admin);

                }else{
                    Reader reader = new Reader();
                    reader.setId(Long.parseLong(row[0]));
                    reader.setUsername(row[1]);
                    reader.setEmail(row[2]);
                    reader.setPassword(row[3]);
                    reader.setFirstName(row[4]);
                    reader.setLastName(row[5]);
                    reader.setDateJoined(row[6]);;
                    users.add(reader);
                }
            }
            userRepository.saveAll(users);
        }catch(IOException e){
            e.printStackTrace();

        }

    }
}
