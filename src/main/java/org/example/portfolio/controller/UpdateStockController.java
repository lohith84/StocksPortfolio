package org.example.portfolio.controller;
import org.example.portfolio.util.CsvParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/update")
public class UpdateStockController {

    @Autowired
    private CsvParser csvParse;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            csvParse.updateStocks(file);
            return ResponseEntity.ok("Stocks are updated.");
        } catch (IOException exception) {
            return ResponseEntity.status(500).body("An error occurred while processing the file.");
        }
    }
}