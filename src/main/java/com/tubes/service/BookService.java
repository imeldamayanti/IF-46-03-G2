package com.tubes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.entity.Book;
import com.tubes.repository.BookListRepository;
import com.tubes.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookListRepository bookListRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findBooksByGenre(genre);
    }

    // Fetch books added by a specific user
    public List<Book> getUserBooks(Long userId) {
        return bookListRepository.findBooksByUserId(userId);
    }
}