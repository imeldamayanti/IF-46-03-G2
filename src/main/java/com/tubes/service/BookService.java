package com.tubes.service;

import com.tubes.entity.Book;
import com.tubes.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import java.util.List;

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
    
    public void updateBook(Long id, Book updatedBook) {
        Book book = getBookById(id); // Ensure book exists
        book.setName(updatedBook.getName());
        book.setAuthor(updatedBook.getAuthor());
        book.setCover(updatedBook.getCover());
        book.setGenre(updatedBook.getGenre());
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long bookId, String name, String author, String genre,
        String dateReleased, int totalPages, String description,
        double rate, String cover
    ) {
        Book existingBook = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        existingBook.setName(name);
        existingBook.setAuthor(author);
        existingBook.setGenre(genre);
        existingBook.setDateReleased(dateReleased);
        existingBook.setTotalPage(totalPages);
        existingBook.setDescription(description);
        existingBook.setRate(rate);
        existingBook.setCover(cover);

        // Save and return the updated book
        return bookRepository.save(existingBook);
    }

    public List<Book> searchBooksByName(String searchQuery) {
        return bookRepository.findBooksByNameContainingIgnoreCase(searchQuery);
    }

    public boolean isBookExists(String name) {
        return bookRepository.existsByName(name);
    }
}
