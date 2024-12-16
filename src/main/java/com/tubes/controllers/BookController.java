package com.tubes.controllers;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/bookdetailAdmin")
    public String bookDetailAdmin() {
        return "bookdetailAdmin";
    }

    @GetMapping("/bookform")
    public String bookform() {
        return "bookform";
    }

    @GetMapping("/formbook")
    public String formbook() {
        return "formbook";
    }
}
