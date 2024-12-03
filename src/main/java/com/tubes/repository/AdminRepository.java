package com.tubes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tubes.entity.Admin;

import jakarta.transaction.Transactional;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE admin", nativeQuery = true)
    void truncateTable();
}
