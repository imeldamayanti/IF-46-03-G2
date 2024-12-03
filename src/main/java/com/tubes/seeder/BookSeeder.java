package com.tubes.seeder;

import com.opencsv.exceptions.CsvException;
import com.tubes.entity.Book;
import com.tubes.repository.BookRepository;
import com.tubes.utils.csvUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

@Component
public class BookSeeder {

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    @Transactional
    public void seedBooks() throws CsvException {
		bookRepository.truncateTable();

		try {
			List<String[]> rows = csvUtils.readCsv("seeds/books.csv");
            List<Book> books = new ArrayList<>();

            for (String[] row : rows) {
				Book book = new Book();

                book.setName(row[1]);
                book.setCover(row[2]);
                book.setAuthor(row[3]);
                book.setGenre(row[4]);
                book.setDateReleased(row[5]);
                book.setTotalPage(Integer.parseInt(row[6]));
                book.setDescription(row[7]);
                // book.setRate(Double.parseDouble(row[8]));

                books.add(book);
            }

            bookRepository.saveAll(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
