package org.example.portfolio.controller;

import org.example.portfolio.dto.PortfolioResponseDTO;
import org.example.portfolio.service.PortfolioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/{userId}")
    public ResponseEntity<PortfolioResponseDTO> getPortfolio(@PathVariable("userId") Long userId) {
        try {
            PortfolioResponseDTO portfolio = portfolioService.getUserPortfolio(userId);
            return ResponseEntity.ok(portfolio);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PortfolioResponseDTO>> getAllPortfolios() {
        try {
            List<PortfolioResponseDTO> portfolios = portfolioService.getAllUserPortfolios();
            return ResponseEntity.ok(portfolios);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}