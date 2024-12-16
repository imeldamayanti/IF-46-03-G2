package com.tubes.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("ROLE_Reader")
public class Reader extends User {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "booklist_id", referencedColumnName = "id")
    private BookList booklist;

    // Default constructor
    public Reader() {}

    // Getter for booklist
    public BookList getBooklist() {
        return booklist;
    }

    // Setter for booklist
    public void setBooklist(BookList booklist) {
        this.booklist = booklist;
    }

    @Override
    public void login() {
        // Implementation for Reader login can go here
    }
}
