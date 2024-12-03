package com.tubes.repository;

import com.tubes.entity.Book;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {
    // findAll provided by JpaRepository

	@Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE book", nativeQuery = true)
    void truncateTable();
}