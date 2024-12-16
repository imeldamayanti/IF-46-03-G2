package com.tubes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tubes.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM information_schema.columns WHERE table_name = 'user' AND column_name = 'dtype'", nativeQuery = true)
    Long checkIfColumnExists();

    @Modifying
    @Transactional
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void disableForeignKeyChecks();
    
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE user", nativeQuery = true)
    void truncateUserTable();

    @Modifying
    @Transactional
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void enableForeignKeyChecks();

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE user DROP COLUMN dtype", nativeQuery = true)
    void dropDtypeColumn();
}
