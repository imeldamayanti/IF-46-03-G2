package com.tubes.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;

public class csvUtils {
    public static List<String[]> readCsv(String fileName) throws IOException {
        try (var is = csvUtils.class.getClassLoader().getResourceAsStream(fileName)) {

            if (is == null) {
                System.out.println("File not found: " + fileName);
                return List.of();
            }

            try (
				var reader = new InputStreamReader(is);
                var csvReader = new CSVReader(reader)
			) {
                return csvReader.readAll();
            } catch (CsvException e) {
                System.err.println("CSV parsing error: " + e.getMessage());
                e.printStackTrace();
                return List.of();  
            }
        } catch (IOException e) {
            System.err.println("IOException when reading the file: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}
