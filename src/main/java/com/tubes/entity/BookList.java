    package com.tubes.entity;

    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.OneToOne;
    import jakarta.persistence.Entity;

    import java.time.LocalDateTime;
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
        private LocalDateTime lastUpdated;
        private ArrayList<Boolean> likedBooks;

        @OneToOne
        @JoinColumn(name = "user_id")
        private User user;

        /**
         * Constructor
         */

        public BookList(){
            this.books = new ArrayList<Book>();
            this.bookCount = 0;
            this.lastUpdated = LocalDateTime.now();
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

        public void addBookToList(Book book){
            if (book != null) {
                this.books.add(book);
                this.likedBooks.add(false); // Default to not liked.
                this.bookCount = books.size();
                this.lastUpdated = LocalDateTime.now();
            }
        }

        public void removeBookFromBookList(Book book){
            int index = this.books.indexOf(book);
            if (index >= 0) {
                this.books.remove(index);
                this.likedBooks.remove(index);
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

        // toLocalDate for debugging purposes
        @Override
        public String toString() {
            return "BookList{id=" + id + ", books='" + books + "', bookCount='" + bookCount + "', lastUpdated=" 
            + lastUpdated + ", likedBooks=" + likedBooks + "'}";
        }

    }
