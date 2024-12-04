package com.tubes.controllers;

import java.util.List;
import com.tubes.service.BookService;
import com.tubes.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String getBooks(Model model) {
        List<Book> books = bookService.getAllBooks();

        model.addAttribute("books", books);

        // Look for a template in : src/main/resources/templates/...
        return "booksExample";
    }
}
