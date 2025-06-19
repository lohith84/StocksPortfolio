package org.example.portfolio.repository;

import org.example.portfolio.entity.TradeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    @BeforeEach
    public void setup() {
        tradeRepository.deleteAll();

        TradeEntity trade1 = new TradeEntity();
        trade1.setUserAccountId(1L);
        trade1.setStockId(1L);
        trade1.setTradeType("Buy");
        trade1.setQuantity(10);
        trade1.setPrice(500.0);
        tradeRepository.save(trade1);

        TradeEntity trade2 = new TradeEntity();
        trade2.setUserAccountId(1L);
        trade2.setStockId(1L);
        trade2.setTradeType("Sell");
        trade2.setQuantity(5);
        trade2.setPrice(520.0);
        tradeRepository.save(trade2);
    }

    @Test
    public void testFindByUserAccountId() {
        List<TradeEntity> trades = tradeRepository.findByUserAccountId(1L);

        assertThat(trades).hasSize(2);
        assertThat(trades.get(0).getTradeType()).isEqualTo("Buy");
        assertThat(trades.get(1).getTradeType()).isEqualTo("Sell");
    }
}