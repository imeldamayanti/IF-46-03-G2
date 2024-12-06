package com.tubes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller 
// @RequestMapping("") //ini buat default dimana, misal di 8080 untuk local host
public class HomeController {

    @GetMapping("/home")
    public String welcome(){    
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

    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }
    

    // nanti logic atau BE nya bakal di sini untuk masing2 page (ini contoh home aja)
}
    