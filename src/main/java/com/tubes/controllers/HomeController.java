package com.tubes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.entity.Book;
import com.tubes.service.BookService;




@Controller 
// @RequestMapping("") //ini buat default dimana, misal di 8080 untuk local host
public class HomeController {
    @Autowired
    private BookController bookController;


    @GetMapping("/")
    public String welcome(Model model){   
      
        List<Book> Fictionbooks = bookController.getFiction("fiction");
        model.addAttribute("books", Fictionbooks);

        List<Book> Comedybooks = bookController.getFiction("comedy");
        model.addAttribute("comedy", Comedybooks);


        return "index"; //ini ambil dari resources/template/index.html
    }

    @GetMapping("/forum")
    public String forum(){
        return "forum";
    }
    
    @GetMapping("/mybooks")
    public String mybooks() {
        return "mybooks";
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
    