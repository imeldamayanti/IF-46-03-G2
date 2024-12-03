// package com.tubes.seeder;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;

// import javax.annotation.PostConstruct;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import com.opencsv.exceptions.CsvException;
// import com.tubes.entity.Admin;
// import com.tubes.repository.AdminRepository;
// import com.tubes.utils.csvUtils;

// import jakarta.transaction.Transactional;

// @Component
// public class AdminSeeder {
//     @Autowired
//     private AdminRepository adminRepository;

//     @PostConstruct
//     @Transactional
//     public void seedAdmin()throws CsvException{
//         adminRepository.truncateTable();
          
//             try{
//                 List<String[]> rows = csvUtils.readCsv("seeds/users.csv");
//                 List<Admin> admins = new ArrayList<>();
    
//                 for(String[] row : rows){
//                     Admin admin = new Admin() ;
                    
//                     // if(row[7]=="admin"){
                        
//                     admin.setUsername(row[1]);

//                     // }

//                     admins.add(admin);                    
//                 }
//                 adminRepository.saveAll(admins);
//             }catch(IOException e){
//                 e.printStackTrace();
    
//             }
    
//         }
// }
