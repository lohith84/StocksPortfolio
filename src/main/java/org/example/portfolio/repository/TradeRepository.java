package org.example.portfolio.repository;

import org.example.portfolio.entity.TradeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Long> {
    List<TradeEntity> findByUserAccountId(Long userAccountId);
}