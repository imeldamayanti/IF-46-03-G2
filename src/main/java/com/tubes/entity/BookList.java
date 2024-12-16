package com.tubes.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class BookList {

    /**
     * Migration
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private List<Book> books;

    private int bookCount;

    private Date lastUpdated;

    private List<Boolean> likedBooks;

    /**
     * Constructor
     */
    public BookList() {
        this.books = new ArrayList<>();
        this.bookCount = 0;
        this.lastUpdated = new Date();
        this.likedBooks = new ArrayList<>();
    }

    /**
     * Getters and Setters
     */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        this.bookCount = books != null ? books.size() : 0;
        this.lastUpdated = new Date();
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Boolean> getLikedBooks() {
        return likedBooks;
    }

    public void setLikedBooks(List<Boolean> likedBooks) {
        this.likedBooks = likedBooks;
    }

    /**
     * Other Methods
     */

    public void addBookToList(Book book) {
        if (book != null) {
            this.books.add(book);
            this.likedBooks.add(false); // Default to not liked.
            this.bookCount = books.size();
            this.lastUpdated = new Date();
        }
    }

    public void removeBookFromBookList(Book book) {
        int index = this.books.indexOf(book);
        if (index >= 0) {
            this.books.remove(index);
            this.likedBooks.remove(index);
            this.bookCount = books.size();
            this.lastUpdated = new Date();
        }
    }

    public void likeTheBook(int bookIndex, boolean isLiked) {
        if (bookIndex >= 0 && bookIndex < this.books.size()) {
            this.likedBooks.set(bookIndex, isLiked);
        }
    }

    public void displayBookList() {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            boolean isLiked = likedBooks.get(i);
            System.out.println(book + " | Liked: " + isLiked);
        }
    }

    // toString for debugging purposes
    @Override
    public String toString() {
        return "BookList{id=" + id + ", bookCount=" + bookCount + ", lastUpdated=" + lastUpdated + "}";
    }
}
