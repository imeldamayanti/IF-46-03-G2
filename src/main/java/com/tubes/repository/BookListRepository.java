package com.tubes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tubes.entity.BookList;

@Repository
public interface BookListRepository extends JpaRepository<BookList, Long> {
    // JpaRepository provides built-in methods for CRUD operations.

    // Custom query to find a BookList by user ID if necessary
    // @Query("SELECT bl FROM BookList bl WHERE bl.user.id = :userId")
    // BookList findByUserId(@Param("userId") Long userId);
}
