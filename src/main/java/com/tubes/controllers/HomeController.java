package com.tubes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.entity.Book;
import com.tubes.entity.User;
import com.tubes.repository.UserRepository;
import com.tubes.service.BookService;

import org.springframework.security.core.Authentication;



@Controller 
public class HomeController {
    @Autowired
    private BookController bookController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String Books(Model model){   

        List<Book> Fictionbooks = bookController.getFiction("fiction");
        model.addAttribute("books", Fictionbooks);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("user", user);

        return "index"; 
    }

    // @GetMapping("/books/{genre}")
    // public String getBooksByGenre(@RequestParam("genre") String genre, Model model) {
    //     List<Book> booksByGenre = bookService.getBooksByGenre(genre);
    //     model.addAttribute("books", booksByGenre);
    //     model.addAttribute("selectedGenre", genre); // Tambahkan genre yang dipilih
    //     return "booksExample"; // Mengarahkan ke file HTML books.html
    // }

  
    @GetMapping("/forum")
    public String forum(){
        return "forum";
    }
    
    @GetMapping("/mybooks")
    public String mybooks() {
        return "mybooks";
    }

    // @GetMapping("/bookdetail")
    // public String bookdetail() {
    //     return "bookdetail";
    // }

    @GetMapping("/faq")
    public String faq(){
        return "faq";
    }

    @GetMapping("/auth")
    public String auth() {
        return "auth";
    }
    
    @GetMapping("signin")
    public String signin() {
        return "signin";
    }

    @GetMapping("signup")
    public String signup() {
        return "signup";
    }
    
    @GetMapping("forgotpw")
    public String forgotpw() {
        return "forgotpw";
    }
    

    @GetMapping("viewall-best")
    public String viewAllBest() {
        return "viewall-best";
    }
    
    // nanti logic atau BE nya bakal di sini untuk masing2 page (ini contoh home aja)
}