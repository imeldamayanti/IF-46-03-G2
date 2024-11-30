package com.tubes.entity;

import jakarta.persistence.*;

@Entity
public class Book {
    /**
     * Migration
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String author;
    private String genre;
    private String dateReleased;
    private Integer totalPage;

    @Lob
    private String description;

    @Column(nullable = true)
    private double rate;

    @Column(nullable = true)
    private String cover;


    /**
     * Constructor
     */
    public Book() {}

    public Book(String name, String author, String genre, String dateReleased, Integer totalPage, String description, double rate, String cover) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.dateReleased = dateReleased;
        this.totalPage = totalPage;
        this.description = description;
        this.rate = rate;
        this.cover = cover;
    }

    
    /**
     * Setter and Getter
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDateReleased() {
        return this.dateReleased;
    }

    public void setDateReleased(String dateReleased) {
        this.dateReleased = dateReleased;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    // toString for debugging purposes
    @Override
    public String toString() {
        return "Book{id=" + id + ", author='" + author + "', genre='" + genre + "', dateReleased=" + dateReleased + ", totalPage=" + totalPage + ", description='" + description + "', rate=" + rate + ", cover='" + cover + "'}";
    }
}
