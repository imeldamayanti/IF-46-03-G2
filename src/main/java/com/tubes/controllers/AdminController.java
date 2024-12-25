package com.tubes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.entity.Book;
import com.tubes.service.BookService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

	@GetMapping("/")
    public String adminPage(
		@RequestParam(value = "genre", required = false) String genre, 
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "12") int size,
        Model model
	){
		Page<Book> books;
        if(genre != null && !genre.isEmpty()){
            books = bookService.getBooksByGenre(genre, page,size);
            model.addAttribute("selectedgenre", genre);
        }else{
            books = bookService.getAllBooks(page, size);
        }

        model.addAttribute("books", books.getContent());
        model.addAttribute("currentPage", books.getNumber() + 1);
        model.addAttribute("totalPages", books.getTotalPages()); 
        model.addAttribute("totalItems", books.getTotalElements());

        return "admin/index";
	}
}