package org.example.portfolio.service;

import org.example.portfolio.entity.StockEntity;

import java.util.List;

public interface StockSearchService {
    List<StockEntity> searchStocksByName(String query);
    List<StockEntity> searchStockByName(String query);
}
