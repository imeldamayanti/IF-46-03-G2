package com.tubes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.entity.Book;
import com.tubes.entity.User;
import com.tubes.repository.BookRepository;
import com.tubes.repository.UserRepository;


@Controller
public class BookListController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/booklist")
    public String showBooklist(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        if (userDetails == null) {
            return "redirect:/signin";
        }

        // Fetch logged-in user
        User user = userRepository.findByUsername(userDetails.getUsername());
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "error";
        }

        // Get user's booklist
        List<Book> booklist = user.getBookList();

        model.addAttribute("user", user);
        model.addAttribute("booklist", booklist);
        return "mybooks"; // Replace with the name of your Thymeleaf template
    }

    @PostMapping("/booklist/add")
    public String addToBooklist(
            @RequestParam("bookId") Long bookId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        if (userDetails == null) {
            return "redirect:/signin";
        }

        // Fetch logged-in user
        User user = userRepository.findByUsername(userDetails.getUsername());
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "error";
        }

        // Fetch the book by its ID
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            model.addAttribute("error", "Book not found.");
            return "error";
        }

        // Add book to user's booklist if it's not already there
        if (!user.getBookList().contains(book)) {
            user.getBookList().add(book);
            userRepository.save(user);
        }

        return "redirect:/mybooks";                                           
    }

    @GetMapping("/mybooks")
    public String viewUserBooklist(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/signin"; // Redirect to sign-in if not logged in
        }

        // Fetch the logged-in user using the username
        User user = userRepository.findByUsername(userDetails.getUsername());
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "error";  // Handle error if user not found
        }

        // Fetch all books added by the user
        List<Book> userBooks = bookRepository.findBooksByUserId(user.getId());

        // Add the books to the model
        model.addAttribute("myBooks", userBooks); // Use 'myBooks' to match Thymeleaf template

        System.out.println("Number of books: " + userBooks.size());
        // Print the books to the console (for debugging)
        System.out.println("Books for user " + user.getUsername() + ": " + userBooks);

        // Return the view for mybooks.html
        return "mybooks";
    }                                                 

    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/mybooks";  // Redirect back to the My Books page after deletion
    }

}