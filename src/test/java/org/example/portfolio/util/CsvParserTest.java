package org.example.portfolio.util;

import org.example.portfolio.PortfolioApplication;
import org.example.portfolio.entity.StockEntity;
import org.example.portfolio.repository.StockRepository;
import org.example.portfolio.util.CsvParser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = PortfolioApplication.class)
public class CsvParserTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CsvParser csvParser;

    private MultipartFile file;

    @BeforeEach
    public void setup() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("sme070125.csv");
        file = new MockMultipartFile("file", classPathResource.getFilename(), "text/csv", classPathResource.getInputStream());
    }

    @Test
    public void testUpdateStocksWithEmptyFile() throws IOException {
        MultipartFile emptyFile = new MockMultipartFile("file", "empty.csv", "text/csv", new byte[0]);

        try {
            csvParser.updateStocks(emptyFile);
        } catch (IOException e) {
            assertThat(e.getMessage()).contains("CSV file is empty");
        }

        StockEntity stock = stockRepository.findByStockName("NonExistentStock");
        assertThat(stock).isNull();
    }

    @Test
    public void testUpdateStocksForExistingStock() throws IOException {
        csvParser.updateStocks(file);

        StockEntity stock = stockRepository.findByStockName("ABS MARINE SERVICES LTD");
        assertThat(stock).isNotNull();
        assertThat(stock.getStockName()).isEqualTo("ABS MARINE SERVICES LTD");

        assertThat(stock.getOpenPrice()).isNotNull();
        assertThat(stock.getClosePrice()).isNotNull();
        assertThat(stock.getLowPrice()).isNotNull();
        assertThat(stock.getHighPrice()).isNotNull();
    }
}