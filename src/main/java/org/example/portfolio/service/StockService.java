package org.example.portfolio.service;

import org.example.portfolio.dto.StockResponseDTO;
import org.example.portfolio.entity.StockEntity;

public interface StockService {
    StockEntity getStockById(Long stockId);
}
