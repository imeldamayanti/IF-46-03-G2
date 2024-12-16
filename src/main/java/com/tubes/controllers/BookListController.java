package com.tubes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tubes.entity.Book;
import com.tubes.entity.BookList;
import com.tubes.entity.Reader;
import com.tubes.entity.User;
import com.tubes.repository.UserRepository;
import com.tubes.service.BookListService;

@RestController
@RequestMapping("/booklist")
public class BookListController {

    @Autowired
    private BookListService bookListService;

    @Autowired
    private UserRepository userRepository;

    // Fetch the book list of the logged-in user
    @GetMapping("/user/{userId}")
    public BookList getUserBookList(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user instanceof Reader reader) { // Ensure it's a Reader
            return reader.getBooklist();
        } else {
            throw new RuntimeException("User does not have a booklist");
        }
    }

    // Add a book to the user's book list
    @PostMapping("/user/{userId}/add")
    public String addBookToBookList(@PathVariable Long userId, @RequestBody Book book) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user instanceof Reader reader) { // Ensure it's a Reader
            bookListService.addBookToList(reader.getBooklist(), book);
            return "Book added successfully to the user's book list";
        } else {
            throw new RuntimeException("User does not have a booklist");
        }
    }

    // Remove a book from the user's book list
    @DeleteMapping("/user/{userId}/remove")
    public String removeBookFromBookList(@PathVariable Long userId, @RequestBody Book book) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user instanceof Reader reader) { // Ensure it's a Reader
            bookListService.removeBookFromBookList(reader.getBooklist(), book);
            return "Book removed successfully from the user's book list";
        } else {
            throw new RuntimeException("User does not have a booklist");
        }
    }

    // Like or unlike a book in the user's book list
    @PutMapping("/user/{userId}/like/{index}")
    public String likeBook(@PathVariable Long userId, @PathVariable int index, @RequestParam boolean isLiked) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user instanceof Reader reader) { // Ensure it's a Reader
            bookListService.likeTheBook(reader.getBooklist(), index, isLiked);
            return "Book like status updated successfully";
        } else {
            throw new RuntimeException("User does not have a booklist");
        }
    }

    // Display the user's book list (as plain data for frontend)
    @GetMapping("/user/{userId}/display")
    public List<Book> displayUserBookList(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user instanceof Reader reader) { // Ensure it's a Reader
            return reader.getBooklist().getBooks();
        } else {
            throw new RuntimeException("User does not have a booklist");
        }
    }
}
