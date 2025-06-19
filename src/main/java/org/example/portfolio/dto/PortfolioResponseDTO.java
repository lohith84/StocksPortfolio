package org.example.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioResponseDTO {
    private List<HoldingDTO> holdings;
    private double totalBuyPrice;
    private double totalCurrentValue;
    private double totalPL;
    private double totalPLPercentage;
}