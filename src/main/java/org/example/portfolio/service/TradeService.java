package org.example.portfolio.service;

import org.example.portfolio.dto.TradeRequestDTO;
import org.example.portfolio.dto.TradeResponseDTO;

public interface TradeService {
    TradeResponseDTO executeTrade(TradeRequestDTO tradeRequest);
}