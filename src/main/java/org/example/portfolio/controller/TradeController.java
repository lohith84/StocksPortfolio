package org.example.portfolio.controller;

import org.example.portfolio.dto.TradeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trade")
public class TradeController {

    private static final String TOPIC_NAME = "trade-requests";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/execute")
    public ResponseEntity<String> executeTrade(@RequestBody TradeRequestDTO tradeRequest) {
        try {
            kafkaTemplate.send(TOPIC_NAME, tradeRequest.getUserAccountId().toString(), tradeRequest);
            return ResponseEntity.ok("Trade request submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to submit trade request: " + e.getMessage());
        }
    }
}