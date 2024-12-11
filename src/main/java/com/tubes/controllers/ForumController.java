package com.tubes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller 
public class ForumController {

    @GetMapping("discuss")
    public String discuss() {
        return "discuss";
    }

    @GetMapping("reply")
    public String reply() {
        return "reply";
    }
    
}