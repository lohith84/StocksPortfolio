package org.example.portfolio.util.Impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import org.example.portfolio.entity.StockEntity;
import org.example.portfolio.util.CsvParser;
import org.example.portfolio.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CsvParserImpl implements CsvParser {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public void updateStocks(MultipartFile file) throws IOException {
        String uploadDir = "uploaded-files/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        Path filePath = Paths.get(uploadDir + file.getOriginalFilename());
        Files.write(filePath, file.getBytes());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            csvParser.forEach(record -> {
                String stockName = record.get("SECURITY");
                StockEntity stock = stockRepository.findByStockName(stockName);
                if (stock == null) {
                    stock = new StockEntity();
                    stock.setStockName(stockName);
                }
                stock.setOpenPrice(Double.parseDouble(record.get("OPEN_PRICE")));
                stock.setClosePrice(Double.parseDouble(record.get("CLOSE_PRICE")));
                stock.setLowPrice(Double.parseDouble(record.get("LOW_PRICE")));
                stock.setHighPrice(Double.parseDouble(record.get("HIGH_PRICE")));
                stockRepository.save(stock);
            });
        }
    }
}