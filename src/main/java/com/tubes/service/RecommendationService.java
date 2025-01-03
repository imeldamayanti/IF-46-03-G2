package com.tubes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.entity.Book;
import com.tubes.repository.BookRepository;

@Service
public class RecommendationService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getRecommendations(Long userId) {
        // Fetch the user's booklist based on user_id
        List<Book> userBooks = bookRepository.findBooksByUserId(userId);

        if (userBooks.isEmpty()) {
            return List.of();  // Return empty list if no books found
        }

        // Fetch all books from the database
        List<Book> allBooks = bookRepository.findAll();

        // Filter out books already in the user's booklist
        allBooks = allBooks.stream()
                           .filter(book -> !userBooks.contains(book))
                           .collect(Collectors.toList());

        // Calculate similarity and sort by similarity
        allBooks.sort((book1, book2) -> {
            double similarity1 = calculateSimilarity(book1, userBooks);
            double similarity2 = calculateSimilarity(book2, userBooks);
            return Double.compare(similarity2, similarity1);  // Sort descending by similarity
        });

        // Print up to 10 recommended books with similarity scores for debugging
        System.out.println("\nRecommended books:");
        allBooks.stream()
                .limit(10)
                .forEach(book -> {
                    double similarity = calculateSimilarity(book, userBooks);  // Recalculate similarity for the current book
                    System.out.println(String.format("ID: %d, Name: %s, Rating: %.2f, Similarity: %.4f", 
                                                     book.getId(), book.getName(), book.getRate(), similarity));
                });

        // Return the top 10 recommendations
        return allBooks.stream()
                       .limit(10)
                       .collect(Collectors.toList());
    }

    // Method to calculate similarity between a book and the user's booklist
    private double calculateSimilarity(Book book, List<Book> userBooks) {
        // Calculate similarity based on genre, author, and total_page
        double genreSimilarity = userBooks.stream()
                                          .anyMatch(userBook -> userBook.getGenre().equals(book.getGenre())) ? 1 : 0;

        double authorSimilarity = userBooks.stream()
                                           .anyMatch(userBook -> userBook.getAuthor().equals(book.getAuthor())) ? 1 : 0;

        // Use absolute difference for total_page similarity (closer page count gives higher similarity)
        double totalPageSimilarity = userBooks.stream()
                                              .mapToDouble(userBook -> 1.0 / (1 + Math.abs(userBook.getTotalPage() - book.getTotalPage())))
                                              .average().orElse(0);

        // Combine the similarity measures (weights can be adjusted if needed)
        return genreSimilarity * 0.4 + authorSimilarity * 0.4 + totalPageSimilarity * 0.2;
    }
}
