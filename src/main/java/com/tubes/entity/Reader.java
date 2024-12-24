package com.tubes.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@DiscriminatorValue("ROLE_Reader")
public class Reader extends User {

    private ArrayList<Book> books;
    private int bookCount;
    private LocalDateTime lastUpdated;
    private ArrayList<Boolean> likedBooks;

    // Default constructor
    public Reader() {
        this.books = new ArrayList<>();
        this.likedBooks = new ArrayList<>();
        this.bookCount = 0;
        this.lastUpdated = LocalDateTime.now();
    }
	
    @Override
    public void login(){

    }

    // Book list (associated books that the user has added)
    @ManyToMany(fetch = FetchType.LAZY)  // Use @ManyToMany for a many-to-many relationship
    @JoinTable(
        name = "user_books", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> bookList = new ArrayList<>();  // User's book list

    // getters and setters
    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        this.bookCount = books != null ? books.size() : 0;
        this.lastUpdated = LocalDateTime.now();
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ArrayList<Boolean> getLikedBooks() {
        return likedBooks;
    }

    public void setLikedBooks(ArrayList<Boolean> likedBooks) {
        this.likedBooks = likedBooks;
    }
    
    /**
        * Other Methods
    */    

    public void addBookToList(Book book) {
        if (book != null) {
            this.books.add(book);
            this.bookList.add(book);  // Sync with JPA list
            this.likedBooks.add(false);  // Default to not liked
            this.bookCount = books.size();
            this.lastUpdated = LocalDateTime.now();
        }
    }
    
    public void removeBookFromBookList(Book book) {
        int index = this.books.indexOf(book);
        if (index >= 0) {
            this.books.remove(index);
            this.likedBooks.remove(index);
            this.bookList.remove(book);  // Sync with JPA list
            this.bookCount = books.size();
            this.lastUpdated = LocalDateTime.now();
        }
    }
    

    public void likeTheBook(int bookIndex, boolean isLiked){
        if (bookIndex >= 0 && bookIndex < this.books.size()) {
            this.likedBooks.set(bookIndex, isLiked);
        } 
    }

    public void displayBookList(){
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            boolean isLiked = likedBooks.get(i);
            System.out.println(book + " | Liked: " + isLiked);
        }
    }
}
