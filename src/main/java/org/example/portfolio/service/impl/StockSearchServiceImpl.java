package org.example.portfolio.service.impl;

import org.example.portfolio.entity.StockEntity;
import org.example.portfolio.repository.StockRepository;
import org.example.portfolio.service.StockSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockSearchServiceImpl implements StockSearchService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<StockEntity> searchStocksByName(String query) {
        return stockRepository.findByStockNameContainingIgnoreCase(query);
    }

    @Override
    public List<StockEntity> searchStockByName(String query) {
        return List.of();
    }
}