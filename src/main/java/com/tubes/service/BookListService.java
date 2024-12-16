package com.tubes.service;

import org.springframework.stereotype.Service;

import com.tubes.entity.Book;
import com.tubes.entity.BookList;

@Service
public class BookListService {

    // Add a book to the book list
    public void addBookToList(BookList bookList, Book book) {
        bookList.addBookToList(book);
    }

    // Remove a book from the book list
    public void removeBookFromBookList(BookList bookList, Book book) {
        bookList.removeBookFromBookList(book);
    }

    // Like or unlike a book in the book list
    public void likeTheBook(BookList bookList, int index, boolean isLiked) {
        bookList.likeTheBook(index, isLiked);
    }

    // Fetch all books in the book list
    public BookList getBookList(BookList bookList) {
        return bookList;
    }
}
