package com.tubes.seeder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tubes.entity.Admin;
import com.tubes.entity.Reader;
import com.tubes.entity.User;
import com.tubes.repository.UserRepository;
import com.tubes.utils.csvUtils;

@Component
public class UserSeeder {

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seedUsers() {
        try {
            // Clear the existing data
            if (userRepository.checkIfColumnExists() > 0) {
                userRepository.dropDtypeColumn();
            }
            userRepository.disableForeignKeyChecks();
            userRepository.truncateUserTable();
            userRepository.enableForeignKeyChecks();

            // Read CSV data
            List<String[]> rows = csvUtils.readCsv("seeds/users.csv");
            List<User> users = new ArrayList<>();

            // Process each row
            for (String[] row : rows) {
                String username = row[1];
                String email = row[2];
                String rawPassword = row[3];
                String firstName = row[4];
                String lastName = row[5];
                LocalDateTime dateJoined = LocalDateTime.now(); 
                String role = row[7];

                if (row[7].equalsIgnoreCase("admin")) {
                    Admin admin = new Admin();
                    admin.setUsername(username);
                    admin.setEmail(email);
                    // admin.setPassword(passwordEncoder.encode(rawPassword));
                    admin.setPassword(rawPassword);
                    admin.setFirstName(firstName);
                    admin.setLastName(lastName);
                    admin.setDateJoined(dateJoined);
                    admin.setRole(role);
                    users.add(admin);
                } else {
                    Reader reader = new Reader();
                    reader.setUsername(username);
                    reader.setEmail(email);
                    // reader.setPassword(passwordEncoder.encode(rawPassword));
                    reader.setPassword(rawPassword);
                    reader.setFirstName(firstName);
                    reader.setLastName(lastName);
                    reader.setRole(role);
                    users.add(reader);
                }
            }

            // Save all users to the database
            userRepository.saveAll(users);

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
