package org.example.portfolio.repository;

import org.example.portfolio.entity.PortfolioEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {
    PortfolioEntity findByUserIdAndStockId(Long userId, Long stockId);
    @Query("SELECT DISTINCT p.userId FROM PortfolioEntity p")
    List<Long> findAllUserIds();
}