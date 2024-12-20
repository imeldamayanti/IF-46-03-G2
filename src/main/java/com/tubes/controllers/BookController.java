package com.tubes.controllers;

import java.util.Arrays;
import java.util.List;

import com.tubes.service.BookService;

import jakarta.servlet.http.HttpServletRequest;

import com.tubes.entity.Book;
import com.tubes.entity.User;
import com.tubes.repository.BookRepository;
import com.tubes.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/books")
    public String getBooks(
        @RequestParam(value = "genre", required = false) String genre, 
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "12") int size,
        @AuthenticationPrincipal UserDetails userDetails,
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
        // model.addAttribute("user", userDetails);
        if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }


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


    // public String getSimilar(@RequestParam(value = "genre", required = false) String genre, Model model) {
    //     Pageable pageable = PageRequest.of(0, 6); 
    //     Page<Book> limitedBooks = bookRepository.findBooksByGenre(genre, pageable);

    //     limitedBooks.forEach(book -> {
    //         String[] words = book.getName().split("\\s+");
    //         if (words.length > 2) {
    //             book.setName(words[0] + " " + words[1]);
    //         }
    //     });

        
    //     model.addAttribute("books", genre);
        
    //     return "bookdetail";
    // }


    // @GetMapping("/bookdetail/{id}")
    // public String getDetail(
    //     @PathVariable("id") Long id, 
    //     @AuthenticationPrincipal UserDetails userDetails,
    //     Model model
    // ) {
    //     Book bookdet = bookService.getBookById(id);
    //     List<String> genres = Arrays.asList(bookdet.getGenre().split(",\\s*"));
        
    //     model.addAttribute("book", bookdet);
    //     model.addAttribute("genres", genres);
    //     if (userDetails != null) {
    //         User user = userRepository.findByUsername(userDetails.getUsername());
    //         model.addAttribute("user", user);
    //     } else {
    //         model.addAttribute("user", null);
    //     }

        
    //     return "bookdetail";
    // }

    @GetMapping("/bookdetail/{id}")
    public String getDetail(
        @PathVariable("id") Long id, 
        @AuthenticationPrincipal UserDetails userDetails,
        Model model
    ) {
        //  Ngambil buku 
        Book bookdet = bookService.getBookById(id);
        List<String> genres = Arrays.asList(bookdet.getGenre().split(",\\s*"));
        
        model.addAttribute("book", bookdet);
        model.addAttribute("genres", genres);
        if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }

        // Ngambil yang mirip2
        String genre = genres.get(0);
        Pageable pageable = PageRequest.of(0, 6); 
        Page<Book> limitedBooks = bookRepository.findBooksByGenre(genre, pageable);
        limitedBooks.forEach(book -> {
            String[] words = book.getName().split("\\s+");
            if (words.length > 2) {
                book.setName(words[0] + " " + words[1]);
            }
        });

        
        model.addAttribute("similar", limitedBooks);
        
        return "bookdetail";
    }


    @GetMapping("/bookdetailAdmin")
    public String bookDetailAdmin() {
        return "bookdetailAdmin";
    }


    @GetMapping("/add")
    public String formbook() {
        return "formbook";
    }


    // @GetMapping("/edit/{id}")
    // public String editBookForm(@PathVariable Long id, Model model) {
    //     Book book = bookService.getBookById(id);
    //     model.addAttribute("book", book);
    //     return "editBook";
    // }

    @PostMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute Book book) {
        bookService.updateBook(id, book);
        return "redirect:/admin/";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, HttpServletRequest request) {
        bookService.deleteBook(id);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/admin/");
    }

    @PostMapping("/books/update")
    public String updateBook(@ModelAttribute Book book, Model model, HttpServletRequest request) {
        bookService.updateBook(
            book.getId(), 
            book.getName(), 
            book.getAuthor(), 
            book.getGenre(), 
            book.getDateReleased(), 
            book.getTotalPage(), 
            book.getDescription(), 
            book.getRate(), 
            book.getCover()
        );
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/admin/");
    }
}
