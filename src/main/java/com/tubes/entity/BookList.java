package com.tubes.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import java.util.ArrayList;

@Entity
public class BookList {
    
    /**
     * Migration
     */

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    private ArrayList<Book> books;
    private int bookCount;
    private String lastUpdated;
    private ArrayList<Boolean> likedBooks;

    /**
     * Constructor
     */

    public BookList(){
        this.books = new ArrayList<Book>();
        this.bookCount = 0;
        this.lastUpdated = "";
        this.likedBooks = new ArrayList<Boolean>();
    }

    /**
     * Getter and Setter
     */    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
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

    public void addBookToList(Book book){
        this.books.add(book);
    }

    public void removeBookFromBookList(Book book){
        this.books.remove(book);
    }

    public void likeTheBook(){
        // Pas user like buku, index buku yang dilike di arraylist books maka arraylist likedbooks di indeks yang sama jadi true
    }

    public void displayBookList(){

    }

    // toString for debugging purposes
    @Override
    public String toString() {
        return "BookList{id=" + id + ", books='" + books + "', bookCount='" + bookCount + "', lastUpdated=" 
        + lastUpdated + ", likedBooks=" + likedBooks + "'}";
    }

}
