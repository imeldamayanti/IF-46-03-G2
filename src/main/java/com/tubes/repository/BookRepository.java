package com.tubes.repository;

import com.tubes.entity.Book;

import jakarta.transaction.Transactional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    // findAll provided by JpaRepository

    @Query("SELECT b FROM Book b WHERE b.genre LIKE %:genre%")
    List<Book> findBooksByGenre(@Param("genre") String genre);
	
    @Modifying
    @Transactional
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void disableForeignKeyChecks();
    
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE user", nativeQuery = true)
    void truncateBookTable();
    
    @Modifying
    @Transactional
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)    
    void enableForeignKeyChecks();
}