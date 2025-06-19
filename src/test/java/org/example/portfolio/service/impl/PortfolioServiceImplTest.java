package org.example.portfolio.service.impl;

import org.example.portfolio.dto.PortfolioResponseDTO;
import org.example.portfolio.dto.HoldingDTO;
import org.example.portfolio.entity.StockEntity;
import org.example.portfolio.entity.TradeEntity;
import org.example.portfolio.repository.PortfolioRepository;
import org.example.portfolio.repository.StockRepository;
import org.example.portfolio.repository.TradeRepository;
import org.example.portfolio.service.PortfolioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PortfolioServiceImplTest {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private EntityManager entityManager;

    private Long userId = 1L;

    @BeforeEach
    public void setup() {
        tradeRepository.deleteAll();
        stockRepository.deleteAll();
        portfolioRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void testGetUserPortfolioWithValidData() {
        StockEntity stock = new StockEntity();
        stock.setStockName("Test Stock");
        stock.setClosePrice(500.0);
        stockRepository.save(stock);
        entityManager.flush();

        TradeEntity trade = new TradeEntity();
        trade.setUserAccountId(userId);
        trade.setStockId(stock.getStockId());
        trade.setTradeType("Buy");
        trade.setQuantity(10);
        trade.setPrice(450.0);
        tradeRepository.save(trade);

        PortfolioResponseDTO portfolio = portfolioService.getUserPortfolio(userId);
        assertThat(portfolio).isNotNull();
        assertThat(portfolio.getHoldings()).hasSize(1);
        HoldingDTO holding = portfolio.getHoldings().getFirst();
        assertThat(holding.getStockName()).isEqualTo("Test Stock");
        assertThat(holding.getQuantity()).isEqualTo(10);
        assertThat(holding.getBuyPrice()).isEqualTo(450.0);
    }

    @Test
    public void testGetUserPortfolioWithNoTrades() {
        PortfolioResponseDTO portfolio = portfolioService.getUserPortfolio(userId);
        assertThat(portfolio).isNotNull();
        assertThat(portfolio.getHoldings()).isEmpty();
        assertThat(portfolio.getTotalPL()).isEqualTo(0.0);
        assertThat(portfolio.getTotalPLPercentage()).isEqualTo(0.0);
    }

    @Test
    public void testGetAllUserPortfolios() {
        StockEntity stock = new StockEntity();
        stock.setStockName("Another Stock");
        stock.setClosePrice(700.0);
        stockRepository.save(stock);
        entityManager.flush();

        TradeEntity trade1 = new TradeEntity();
        trade1.setUserAccountId(userId);
        trade1.setStockId(stock.getStockId());
        trade1.setTradeType("Buy");
        trade1.setQuantity(5);
        trade1.setPrice(650.0);
        tradeRepository.save(trade1);

        TradeEntity trade2 = new TradeEntity();
        trade2.setUserAccountId(2L);
        trade2.setStockId(stock.getStockId());
        trade2.setTradeType("Buy");
        trade2.setQuantity(8);
        trade2.setPrice(670.0);
        tradeRepository.save(trade2);
    }
}