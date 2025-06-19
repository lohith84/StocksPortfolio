package org.example.portfolio.service;

import org.example.portfolio.dto.TradeRequestDTO;
import org.example.portfolio.dto.TradeResponseDTO;
import org.example.portfolio.entity.PortfolioEntity;
import org.example.portfolio.entity.StockEntity;
import org.example.portfolio.repository.PortfolioRepository;
import org.example.portfolio.repository.StockRepository;
import org.example.portfolio.repository.TradeRepository;
import org.example.portfolio.service.impl.TradeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    private StockEntity stock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        stock = new StockEntity();
        stock.setStockId(1L);
        stock.setStockName("Test Stock");
        stock.setOpenPrice(100.0);
        stock.setClosePrice(110.0);
        stock.setHighPrice(115.0);
        stock.setLowPrice(90.0);
    }

    @Test
    public void testExecuteBuyTrade() {
        TradeRequestDTO tradeRequest = new TradeRequestDTO();
        tradeRequest.setUserAccountId(1L);
        tradeRequest.setTradeType("Buy");
        tradeRequest.setQuantity(10);
        tradeRequest.setStockId(stock.getStockId());

        when(stockRepository.findById(stock.getStockId())).thenReturn(Optional.of(stock));
        when(portfolioRepository.findByUserIdAndStockId(1L, stock.getStockId())).thenReturn(null);

        TradeResponseDTO response = tradeService.executeTrade(tradeRequest);

        assertThat(response.getStatus()).isEqualTo("Success");
        assertThat(response.getMessage()).isEqualTo("Trade executed successfully");

        verify(portfolioRepository, times(1)).save(any(PortfolioEntity.class));
        verify(tradeRepository, times(1)).save(any());
    }

    @Test
    public void testExecuteSellTradeWithInsufficientHoldings() {
        TradeRequestDTO tradeRequest = new TradeRequestDTO();
        tradeRequest.setUserAccountId(1L);
        tradeRequest.setTradeType("Sell");
        tradeRequest.setQuantity(10);
        tradeRequest.setStockId(stock.getStockId());

        when(stockRepository.findById(stock.getStockId())).thenReturn(Optional.of(stock));
        when(portfolioRepository.findByUserIdAndStockId(1L, stock.getStockId())).thenReturn(null);

        TradeResponseDTO response = tradeService.executeTrade(tradeRequest);

        assertThat(response.getStatus()).isEqualTo("Failure");
        assertThat(response.getMessage()).isEqualTo("Insufficient holdings for sell trade");

        verify(portfolioRepository, never()).save(any());
        verify(tradeRepository, never()).save(any());
    }

    @Test
    public void testExecuteTradeStockNotFound() {
        TradeRequestDTO tradeRequest = new TradeRequestDTO();
        tradeRequest.setUserAccountId(1L);
        tradeRequest.setTradeType("Buy");
        tradeRequest.setQuantity(10);
        tradeRequest.setStockId(99L);

        when(stockRepository.findById(99L)).thenReturn(Optional.empty());

        TradeResponseDTO response = tradeService.executeTrade(tradeRequest);

        assertThat(response.getStatus()).isEqualTo("Failure");
        assertThat(response.getMessage()).isEqualTo("Stock not found");

        verify(portfolioRepository, never()).save(any());
        verify(tradeRepository, never()).save(any());
    }
}