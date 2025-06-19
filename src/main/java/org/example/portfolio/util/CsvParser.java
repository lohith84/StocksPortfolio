package org.example.portfolio.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CsvParser {
    void updateStocks(MultipartFile file) throws IOException;
}