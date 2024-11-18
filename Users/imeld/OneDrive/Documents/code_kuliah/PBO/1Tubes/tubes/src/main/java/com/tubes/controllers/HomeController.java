package com.tubes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping("") //ini buat default dimana, misal di 8080 untuk local host
public class HomeController {

    @GetMapping
    public String welcome(){    
        return "index"; //ini ambil dari resources/template/index.html
    }

    // nanti logic atau BE nya bakal di sini untuk masing2 page (ini contoh home aja)
}
