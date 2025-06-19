package org.example.portfolio.service.impl;

import org.example.portfolio.PortfolioApplication;
import org.example.portfolio.dto.TradeRequestDTO;
import org.example.portfolio.dto.TradeResponseDTO;
import org.example.portfolio.entity.PortfolioEntity;
import org.example.portfolio.entity.StockEntity;
import org.example.portfolio.repository.PortfolioRepository;
import org.example.portfolio.repository.StockRepository;
import org.example.portfolio.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = PortfolioApplication.class)
public class TradeServiceImplTest {

    @Autowired
    private TradeServiceImpl tradeService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private TradeRepository tradeRepository;

    private StockEntity stock;

    @BeforeEach
    public void setup() {
        stockRepository.deleteAll();
        portfolioRepository.deleteAll();
        tradeRepository.deleteAll();

        stock = new StockEntity();
        stock.setStockName("Test Stock");
        stock.setOpenPrice(100.0);
        stock.setClosePrice(110.0);
        stock.setHighPrice(115.0);
        stock.setLowPrice(90.0);
        stock = stockRepository.save(stock);
    }

    @Test
    public void testExecuteBuyTrade() {
        TradeRequestDTO tradeRequest = new TradeRequestDTO();
        tradeRequest.setUserAccountId(1L);
        tradeRequest.setTradeType("Buy");
        tradeRequest.setQuantity(10);
        tradeRequest.setStockId(stock.getStockId());

        TradeResponseDTO response = tradeService.executeTrade(tradeRequest);
        assertThat(response.getStatus()).isEqualTo("Success");
        assertThat(response.getMessage()).isEqualTo("Trade executed successfully");

        PortfolioEntity portfolio = portfolioRepository.findByUserIdAndStockId(1L, stock.getStockId());
        assertThat(portfolio).isNotNull();
        assertThat(portfolio.getQuantity()).isEqualTo(10);
        assertThat(portfolio.getBuyPrice()).isEqualTo(110.0);
    }

    @Test
    public void testExecuteSellTradeWithInsufficientHoldings() {
        TradeRequestDTO tradeRequest = new TradeRequestDTO();
        tradeRequest.setUserAccountId(1L);
        tradeRequest.setTradeType("Sell");
        tradeRequest.setQuantity(5);
        tradeRequest.setStockId(stock.getStockId());

        TradeResponseDTO response = tradeService.executeTrade(tradeRequest);
        assertThat(response.getStatus()).isEqualTo("Failure");
        assertThat(response.getMessage()).isEqualTo("Insufficient holdings for sell trade");
    }
}