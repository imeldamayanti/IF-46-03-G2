package com.tubes.service;

import com.tubes.entity.Book;
import com.tubes.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Page<Book> getAllBooks(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Book> booksPage = bookRepository.findAll(pageable);

        return booksPage;
    }

    public Page<Book> getBooksByGenre(
        String genre,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Book> booksPage = bookRepository.findBooksByGenre(genre, pageable);

        return booksPage;
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book Not Found!"+id));
    }
    

}