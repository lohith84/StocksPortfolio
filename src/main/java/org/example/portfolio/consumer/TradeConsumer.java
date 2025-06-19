package org.example.portfolio.consumer;

import org.example.portfolio.dto.TradeRequestDTO;
import org.example.portfolio.dto.TradeResponseDTO;
import org.example.portfolio.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class TradeConsumer {

    private static final Logger LOGGER = Logger.getLogger(TradeConsumer.class.getName());

    @Autowired
    private TradeService tradeService;

    @KafkaListener(topics = "trade-requests", groupId = "trade-group")
    public void processTrade(TradeRequestDTO tradeRequest) {
        try {
            LOGGER.info("Received trade request: " + tradeRequest);
            TradeResponseDTO response = tradeService.executeTrade(tradeRequest);
            LOGGER.info("Trade processed successfully: " + response.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Failed to process trade request for userAccountId="
                    + tradeRequest.getUserAccountId() + " - " + e.getMessage());
        }
    }
}