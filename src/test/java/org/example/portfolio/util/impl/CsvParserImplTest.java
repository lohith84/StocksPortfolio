package org.example.portfolio.util.impl;

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
public class CsvParserImplTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CsvParser csvParser;

    private MultipartFile validFile;

    @BeforeEach
    public void setup() throws IOException {
        ClassPathResource validResource = new ClassPathResource("sme070125.csv");
        validFile = new MockMultipartFile("file", validResource.getFilename(), "text/csv", validResource.getInputStream());
    }

    @Test
    public void testUpdateStocksWithMissingColumns() throws IOException {
        MultipartFile invalidFile = new MockMultipartFile(
                "file",
                "sme070125_missing_columns.csv",
                "text/csv",
                new byte[0] // Simulating an invalid or incomplete file
        );

        try {
            csvParser.updateStocks(invalidFile);
        } catch (Exception e) {
            assertThat(e.getMessage()).contains("Invalid CSV headers");
        }
    }

    @Test
    public void testUpdateStocksWithValidFile() throws IOException {
        csvParser.updateStocks(validFile);

        StockEntity stock = stockRepository.findByStockName("AATMAJ HEALTHCARE LIMITED");
        assertThat(stock).isNotNull();
        assertThat(stock.getStockName()).isEqualTo("AATMAJ HEALTHCARE LIMITED");

        assertThat(stock.getOpenPrice()).isNotNull();
        assertThat(stock.getClosePrice()).isNotNull();
        assertThat(stock.getLowPrice()).isNotNull();
        assertThat(stock.getHighPrice()).isNotNull();
    }
}