package com.tubes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.entity.Book;
import com.tubes.entity.User;
import com.tubes.repository.UserRepository;


import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;



@Controller 
public class HomeController {
    @Autowired
    private BookController bookController;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String Books(@RequestParam(value = "genre", required = false) String genre , Model model){   

        Page<Book> Biography = bookController.getByGenre("iography");
        model.addAttribute("bbooks", Biography);

        Page<Book> Fiction = bookController.getByGenre("iction");
        model.addAttribute("fbooks", Fiction);

        Page<Book> Romance = bookController.getByGenre("omance");
        model.addAttribute("rbooks", Romance);

        Page<Book> History = bookController.getByGenre("istory");
        model.addAttribute("hbooks", History);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("user", user);

        return "index"; 
    }


    @Controller
    public class MyBooksController {

        @GetMapping("/mybooks")
        public String showMyBooksPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
            if (userDetails == null) {
                // Jika user tidak login, arahkan ke halaman login
                return "redirect:/signin";
            }

            // Jika login, tampilkan halaman MyBooks
            model.addAttribute("user", userDetails);
            return "mybooks";
        }
    }


    @GetMapping("/bookdetail")
    public String bookdetail() {
        return "bookdetail";
    }

    @GetMapping("/faq")
    public String faq(){
        return "faq";
    }

    @GetMapping("/auth")
    public String auth() {
        return "auth";
    }
    
    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    
    @GetMapping("/forgotpw")
    public String forgotpw() {
        return "forgotpw";
    }
    

    @GetMapping("viewall-best")
    public String viewAllBest() {
        return "viewall-best";
    }
    
}