package com.tubes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tubes.entity.BookList;
import com.tubes.entity.User;

@Repository
public interface BookListRepository extends JpaRepository<BookList, Long> {
    // You can add custom query methods if needed

    // This method automatically generates the query to fetch BookList by User
    BookList findByUser(User user);
}