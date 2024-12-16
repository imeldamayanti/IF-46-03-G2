package com.tubes.service;

import java.util.List;
import com.tubes.entity.Book;
import com.tubes.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findBooksByGenre(genre);
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book Not Found!"+id));
    }
    

}