package com.tubes.repository;

import com.tubes.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // findAll provided by JpaRepository

	// 
}