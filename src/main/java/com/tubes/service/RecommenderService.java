package com.tubes.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tubes.entity.Book;

@Service
public class RecommenderService {

    @Autowired
    private BookListService bookListService; // Service to fetch user books

    public List<Book> getRecommendations(Long userId) throws IOException {
        // Step 1: Fetch user books
        List<Book> userBooks = bookListService.getUserBooks(userId);

        // Step 2: Prepare input JSON
        List<Book> allBooks = bookListService.getAllBooks(); // Fetch all available books
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> input = new HashMap<>();
        input.put("userBooks", userBooks);
        input.put("allBooks", allBooks);

        String inputJson = objectMapper.writeValueAsString(input);

        // Step 3: Call Python script
        ProcessBuilder processBuilder = new ProcessBuilder("python3", "python/recommender.py");
        Process process = processBuilder.start();

        // Send JSON input to Python script
        try (OutputStream os = process.getOutputStream()) {
            os.write(inputJson.getBytes());
            os.flush();
        }

        // Step 4: Read Python script output
        String outputJson;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            outputJson = reader.lines().collect(Collectors.joining());
        }

        // Step 5: Convert Python output back to Java objects
        List<Book> recommendedBooks = objectMapper.readValue(outputJson, new TypeReference<List<Book>>() {});

        return recommendedBooks;
    }
}
