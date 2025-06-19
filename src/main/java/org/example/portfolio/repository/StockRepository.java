package org.example.portfolio.repository;

import org.example.portfolio.entity.StockEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
    StockEntity findByStockName(String stockName);
    StockEntity findByStockId(Long stockId);
    List<StockEntity> findByStockNameContainingIgnoreCase(String name);
}
