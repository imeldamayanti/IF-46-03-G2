package com.tubes.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tubes.entity.Book;
import com.tubes.entity.Reader;
import com.tubes.repository.BookRepository;
import com.tubes.repository.UserRepository;

public class ReaderService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public String addBookToReader(String username, Long bookId) {
        Reader reader = (Reader) userRepository.findByUsername(username);
        if (reader == null) {
            return "User not found";
        }

        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return "Book not found";
        }

        if (!reader.getBookList().contains(book)) {
            reader.addBookToList(book);
            userRepository.save(reader);
            return "Book added";
        }

        return "Book already in the list";
    }
}
