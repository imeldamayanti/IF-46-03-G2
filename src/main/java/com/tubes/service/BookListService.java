package com.tubes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.entity.Book;
import com.tubes.entity.User;
import com.tubes.repository.BookRepository;
import com.tubes.repository.UserRepository;

@Service
public class BookListService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public void addBookToUserList(Long bookId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Get the user's book list (List<Book>) and add the book if not already present.
        if (!user.getBookList().contains(book)) {
            user.getBookList().add(book);
            userRepository.save(user); // Save the user to persist changes.
        }
    }
}
