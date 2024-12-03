// package com.tubes.entity;

// import jakarta.persistence.*;

// @Entity
// public class AddedBook {
    
//     /**
//         * Migration
//     */
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private Book bookAdded;
//     private boolean bookLikes;

//     /**
//         * Constructor
//     */

//     public AddedBook(){}

//     public AddedBook(Book bookAdded, boolean bookLikes){
//         this.bookAdded = bookAdded;
//         this.bookLikes = bookLikes;
//     }
    
//     /**
//         * Setter and Getter
//     */

//     public Book getBookAdded(){
//         return this.bookAdded;
//     }

//     public void setBookAdded(Book bookAdded){
//         this.bookAdded = bookAdded;
//     }

//     public boolean getLikeBook(){
//         return this.bookLikes;
//     }

//     public void setLikeBook(boolean likeBook){
//         this.bookLikes = likeBook;
//     }

//     /**
//         * Other Methods
//     */

//     public void likeTheBook(boolean isLiked){
//         setLikeBook(isLiked);
//     }

//     // toString for debugging purposes
//     @Override
//     public String toString() {
//         return "AddedBook{bookAdded=" + bookAdded + ", bookLikes=" + bookLikes + "'}";
//     }    

// }
