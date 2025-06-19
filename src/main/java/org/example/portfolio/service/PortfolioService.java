package org.example.portfolio.service;

import org.example.portfolio.dto.PortfolioResponseDTO;

import java.util.List;

public interface PortfolioService {
    PortfolioResponseDTO getUserPortfolio(Long userId);
    List<PortfolioResponseDTO> getAllUserPortfolios();
}