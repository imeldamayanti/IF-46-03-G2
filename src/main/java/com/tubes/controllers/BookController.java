package com.tubes.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tubes.entity.Book;
import com.tubes.entity.User;
import com.tubes.repository.BookRepository;
import com.tubes.repository.UserRepository;
import com.tubes.service.BookService;

import jakarta.servlet.http.HttpServletRequest;


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
        // Ambil detail buku
        Book bookdet = bookService.getBookById(id);
        List<String> genres = Arrays.asList(bookdet.getGenre().split(",\\s*"));
    
        model.addAttribute("book", bookdet);
        model.addAttribute("genres", genres);
    
        boolean isInBooklist = false;
    
        // Cek apakah user sudah login
        if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername());
            model.addAttribute("user", user);
    
            // Cek apakah buku sudah ada di daftar user
            isInBooklist = user.getBookList().contains(bookdet);
        } else {
            model.addAttribute("user", null);
        }
    
        model.addAttribute("isInBooklist", isInBooklist);
    
        // Ambil buku-buku lain dengan genre yang sama
        String genre = genres.get(0);
        Long bookIdToExclude = bookdet.getId();
        Pageable pageable = PageRequest.of(0, 6); 
        Page<Book> limitedBooks = bookRepository.findBooksByGenre(genre, pageable);
        if(limitedBooks.getTotalElements()>6){
            pageable = PageRequest.of(1, 6); 
            limitedBooks = bookRepository.findBooksByGenre(genre, pageable);
        }

        List<Book> filteredBooks = limitedBooks.stream()
        .filter(book -> !book.getId().equals(bookIdToExclude)) // Mengecualikan buku dengan ID yang sama
        .collect(Collectors.toList());

        filteredBooks.forEach(book -> {
    String[] words = book.getName().split("\\s+");
        if (words.length > 2) {
            book.setName(words[0] + " " + words[1]);
        }
    });

    model.addAttribute("similar", filteredBooks);

    return "bookdetail";
    }
    

    @GetMapping("/bookdetailAdmin")
    public String bookDetailAdmin() {
        return "bookdetailAdmin";
    }

    @GetMapping({"/admin/add", "/admin/edit/{id}"})
    public String formbook(@PathVariable(required = false) Long id, Model model) {
        Book book = id != null ? bookService.getBookById(id) : new Book();
        model.addAttribute("book", book);
        return "formbook";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") Book book, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = LocalDate.parse(book.getDateReleased(), DateTimeFormatter.ISO_DATE).format(formatter);
            book.setDateReleased(formattedDate);
            // hm
            bookService.saveBook(book);
            redirectAttributes.addFlashAttribute("message", "Book saved successfully!");
            Long id = book.getId();
            return "redirect:/bookdetail/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to save book. Please try again.");
            String referer = request.getHeader("Referer");
            return "redirect:" + (referer != null ? referer : "/admin/");
        }
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

    @GetMapping("/search")
    @ResponseBody
    public List<Book> searchBooks(@RequestParam("searchQuery") String searchQuery) {
        // Cari buku berdasarkan nama
        List<Book> books = bookRepository.findBooksByNameContainingIgnoreCase(searchQuery);
        return books; // Return results as JSON
    }


}
