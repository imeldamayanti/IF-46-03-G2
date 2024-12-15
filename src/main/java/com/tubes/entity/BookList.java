import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookList {

    private List<Book> books; // List of books in the booklist
    private int bookCount;    // Total count of books
    private Date lastUpdated; // Date when the booklist was last updated
    private List<Boolean> likedBooks; // List to store liked status of books

    // Constructor
    public BookList() {
        books = new ArrayList<>();
        likedBooks = new ArrayList<>();
        bookCount = 0;
        lastUpdated = new Date();
    }

    // Getter and Setter for books
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        this.bookCount = books.size();
        this.lastUpdated = new Date();
    }

    // Getter and Setter for bookCount
    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    // Getter and Setter for lastUpdated
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // Getter and Setter for likedBooks
    public List<Boolean> getLikedBooks() {
        return likedBooks;
    }

    public void setLikedBooks(List<Boolean> likedBooks) {
        this.likedBooks = likedBooks;
    }

    // Add a book to the list
    public void addBookToList(Book book) {
        books.add(book);
        likedBooks.add(false); // Default liked status is false
        bookCount++;
        lastUpdated = new Date();
    }

    // Remove a book from the list
    public void removeBookFromBookList(Book book) {
        int index = books.indexOf(book);
        if (index != -1) {
            books.remove(index);
            likedBooks.remove(index);
            bookCount--;
            lastUpdated = new Date();
        }
    }

    // Like or unlike a book in the list
    public void likeTheBook(int index, boolean isLiked) {
        if (index >= 0 && index < likedBooks.size()) {
            likedBooks.set(index, isLiked);
            lastUpdated = new Date();
        }
    }

    // Display the book list
    public void displayBookList() {
        System.out.println("Book List:");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            boolean isLiked = likedBooks.get(i);
            System.out.println((i + 1) + ". " + book.getName() + " (Liked: " + isLiked + ")");
        }
        System.out.println("Total Books: " + bookCount);
        System.out.println("Last Updated: " + lastUpdated);
    }
}
