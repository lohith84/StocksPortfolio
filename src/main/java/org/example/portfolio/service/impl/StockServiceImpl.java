package org.example.portfolio.service.impl;

import org.example.portfolio.entity.StockEntity;
import org.example.portfolio.repository.StockRepository;
import org.example.portfolio.service.StockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockRepository stockRepository;

    public StockEntity getStockById(Long stockId) {
        return stockRepository.findByStockId(stockId);
    }

}