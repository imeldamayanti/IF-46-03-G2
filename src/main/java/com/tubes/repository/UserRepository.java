package com.tubes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tubes.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);

    // @Modifying
    // @Transactional
    // @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    // void disableForeignKeyChecks();
    
    // @Modifying
    // @Transactional
    // @Query(value = "TRUNCATE TABLE book", nativeQuery = true)
    // void truncateUserTable();
    
    // @Modifying
    // @Transactional
    // @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    // void enableForeignKeyChecks();
    
    // function dimana dimulai dari 1 lagi

    // @Modifying
    // @Transactional
    // @Query(value = "ALTER TABLE book AUTO_INCREMENT = 1", nativeQuery = true)
    // void resetAutoIncrement();
    
}
