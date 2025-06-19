package org.example.portfolio.service;

import jakarta.persistence.EntityManager;
import org.example.portfolio.dto.PortfolioResponseDTO;
import org.example.portfolio.entity.StockEntity;
import org.example.portfolio.entity.TradeEntity;
import org.example.portfolio.repository.StockRepository;
import org.example.portfolio.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PortfolioServiceTest {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private EntityManager entityManager;

    private Long userId = 1L;

    @BeforeEach
    public void setup() {
        tradeRepository.deleteAll();
        stockRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        StockEntity stock = new StockEntity();
        stock.setStockName("Test Stock");
        stock.setClosePrice(500.0);
        stockRepository.save(stock);

        TradeEntity trade1 = new TradeEntity();
        trade1.setUserAccountId(userId);
        trade1.setStockId(stock.getStockId());
        trade1.setTradeType("Buy");
        trade1.setQuantity(10);
        trade1.setPrice(450.0);
        tradeRepository.save(trade1);

        TradeEntity trade2 = new TradeEntity();
        trade2.setUserAccountId(userId);
        trade2.setStockId(stock.getStockId());
        trade2.setTradeType("Buy");
        trade2.setQuantity(5);
        trade2.setPrice(460.0);
        tradeRepository.save(trade2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void testGetUserPortfolio() {
        PortfolioResponseDTO portfolio = portfolioService.getUserPortfolio(userId);

        assertThat(portfolio).isNotNull();
        assertThat(portfolio.getHoldings()).hasSize(1);
        assertThat(portfolio.getHoldings().getFirst().getStockName()).isEqualTo("Test Stock");
        assertThat(portfolio.getHoldings().getFirst().getQuantity()).isEqualTo(15);
    }

    @Test
    public void testGetUserPortfolioWithMultipleTrades() {
        PortfolioResponseDTO portfolio = portfolioService.getUserPortfolio(userId);

        assertThat(portfolio).isNotNull();
        assertThat(portfolio.getHoldings()).hasSize(1);

        var holding = portfolio.getHoldings().getFirst();
        assertThat(holding.getStockName()).isEqualTo("Test Stock");
        assertThat(holding.getQuantity()).isEqualTo(15);
        assertThat(holding.getBuyPrice()).isBetween(450.0, 460.0);
        assertThat(holding.getCurrentPrice()).isEqualTo(500.0);
        assertThat(holding.getTotalValue()).isEqualTo(15 * 500.0);
    }
}