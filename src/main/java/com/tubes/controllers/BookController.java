package com.tubes.controllers;

import java.util.Arrays;
import java.util.List;

import com.tubes.service.BookService;
import com.tubes.entity.Book;
import com.tubes.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public String getBooks(
        @RequestParam(value = "genre", required = false) String genre, 
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "12") int size,
        Model model
    ) {
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

        return "booksExample";
    }
  

    public Page<Book> getByGenre(@RequestParam(value = "genre", required = false) String genre) {
        Pageable pageable = PageRequest.of(0, 6); 
        Page<Book> limitedBooks = bookRepository.findBooksByGenre(genre, pageable);

        limitedBooks.forEach(book -> {
            String[] words = book.getName().split("\\s+");
            if (words.length > 2) {
                book.setName(words[0] + " " + words[1]);
            }
        });
        return limitedBooks;
    }

    @GetMapping("/bookdetail/{id}")
    public String getDetail(@PathVariable("id") Long id, Model model) {
        Book bookdet = bookService.getBookById(id);
        List<String> genres = Arrays.asList(bookdet.getGenre().split(",\\s*"));
        
        model.addAttribute("book", bookdet);
        model.addAttribute("genres", genres);

        return "bookdetail";
    }

    @GetMapping("/bookdetailAdmin")
    public String bookDetailAdmin() {
        return "bookdetailAdmin";
    }


    @GetMapping("/formbook")
    public String formbook() {
        return "formbook";
    }
}
