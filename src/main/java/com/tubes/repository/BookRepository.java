package com.tubes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tubes.entity.Book;

import jakarta.transaction.Transactional;


public interface BookRepository extends JpaRepository<Book, Long> {
    // findAll provided by JpaRepository

    // @Query("SELECT b FROM Book b WHERE b.genre LIKE %:genre%")
    @Query("SELECT b FROM Book b WHERE b.genre LIKE %:genre%")
    Page<Book> findBooksByGenre(@Param("genre") String genre, Pageable pageable);
 
    
    @Query(value = "SELECT COUNT(*) FROM information_schema.columns WHERE table_name = 'book'", nativeQuery = true)
    Long checkIfColumnExists();


    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE book", nativeQuery = true)
    void truncateBookTable();
 
    @Query("SELECT b FROM Book b WHERE b.user.id = :userId")
    List<Book> findBooksByUserId(@Param("userId") Long userId);

}