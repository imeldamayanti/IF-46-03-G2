package com.tubes.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.tubes.service.BookService;
import com.tubes.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String getBooks(@RequestParam(value = "genre", required = false) String genre, Model model) {
        List<Book> books;
        if(genre != null && !genre.isEmpty()){
            books = bookService.getBooksByGenre(genre);
            model.addAttribute("selectedgenre", genre);
        }else{
            books = bookService.getAllBooks();
        }

        model.addAttribute("books", books);

        return "booksExample";
    }
    // @GetMapping("/books")
    // public String getBooks(Model model) {
    //     List<Book> books = bookService.getAllBooks();
    //     model.addAttribute("books", books);

    //     return "booksExample";
    // }

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

    @GetMapping("/bookdetail/{id}")
    public String getDetail(@PathVariable("id") Long id, Model model) {
        Book bookdet = bookService.getBookById(id);
        List<String> genres = Arrays.asList(bookdet.getGenre().split(",\\s*"));
        
        model.addAttribute("book", bookdet);
        model.addAttribute("genres", genres);

        return "bookdetail";
    }
    
   

}
