package com.tubes.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.tubes.service.BookService;
import com.tubes.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String getBooks(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        Model model
    ) {
        Page<Book> books = bookService.getAllBooks(page, size);

        model.addAttribute("books", books.getContent());
        model.addAttribute("currentPage", books.getNumber() + 1);
        model.addAttribute("totalPages", books.getTotalPages()); 
        model.addAttribute("totalItems", books.getTotalElements());

        // Look for a template in : src/main/resources/templates/...
        return "booksExample";
    }

    public List<Book> getFiction(String genre) {
        List<Book> Fictionbooks = bookService.getBooksByGenre(genre);
        List<Book> LimitedBooks = Fictionbooks.stream().limit(6).collect(Collectors.toList());

        LimitedBooks.forEach(book -> {
            String[] words = book.getName().split("\\s+");
            if (words.length > 2) {
                book.setName(words[0] + " " + words[1]);
            }
        });
        return LimitedBooks;
    }
}
