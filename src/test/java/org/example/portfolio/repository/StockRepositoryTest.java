package org.example.portfolio.repository;
import org.example.portfolio.PortfolioApplication;
import org.example.portfolio.entity.StockEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = PortfolioApplication.class)
public class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;
    private StockEntity savedStock;
    @Test
    public void testDatabaseConnection() {
        StockEntity stock = new StockEntity();
        stock.setStockName("Test_Stock");
        stock.setOpenPrice(200.0);
        stock.setClosePrice(250.0);
        stock.setHighPrice(300.0);
        stock.setLowPrice(150.0);
        savedStock = stockRepository.save(stock);
        StockEntity retrievedStock = stockRepository.findByStockName("Test_Stock");
        assertThat(retrievedStock).isNotNull();
        assertThat(retrievedStock.getStockName()).isEqualTo(savedStock.getStockName());
        assertThat(retrievedStock.getOpenPrice()).isEqualTo(savedStock.getOpenPrice());
        assertThat(retrievedStock.getClosePrice()).isEqualTo(savedStock.getClosePrice());
        assertThat(retrievedStock.getHighPrice()).isEqualTo(savedStock.getHighPrice());
        assertThat(retrievedStock.getLowPrice()).isEqualTo(savedStock.getLowPrice());
    }

    @AfterEach
    public void cleanUp() {
        if (savedStock != null) {
            stockRepository.delete(savedStock);
        }
    }
}
