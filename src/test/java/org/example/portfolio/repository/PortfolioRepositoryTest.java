package org.example.portfolio.repository;

import org.example.portfolio.PortfolioApplication;
import org.example.portfolio.entity.PortfolioEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = PortfolioApplication.class)
public class PortfolioRepositoryTest {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Test
    public void testFindByUserIdAndStockId() {
        PortfolioEntity existingPortfolio = portfolioRepository.findByUserIdAndStockId(1L, 100L);
        if (existingPortfolio != null) {
            portfolioRepository.delete(existingPortfolio);
        }
        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setUserId(1L);
        portfolio.setStockId(100L);
        portfolio.setQuantity(20);
        portfolio.setBuyPrice(150.0);
        portfolioRepository.save(portfolio);
        PortfolioEntity found = portfolioRepository.findByUserIdAndStockId(1L, 100L);
        assertThat(found).isNotNull();
        assertThat(found.getQuantity()).isEqualTo(20);
        assertThat(found.getBuyPrice()).isEqualTo(150.0);
    }

    @Test
    public void testFindAllUserIds() {
        PortfolioEntity portfolio1 = new PortfolioEntity();
        portfolio1.setUserId(1L);
        portfolio1.setStockId(101L);
        portfolioRepository.save(portfolio1);

        PortfolioEntity portfolio2 = new PortfolioEntity();
        portfolio2.setUserId(2L);
        portfolio2.setStockId(102L);
        portfolioRepository.save(portfolio2);

        List<Long> userIds = portfolioRepository.findAllUserIds();
        assertThat(userIds).contains(1L, 2L);
    }
}