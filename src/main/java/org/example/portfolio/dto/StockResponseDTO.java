package org.example.portfolio.dto;

import lombok.Data;

@Data
public class StockResponseDTO {
    private Long stockId;
    private String stockName;
    private Double openPrice;
    private Double closePrice;
    private Double highPrice;
    private Double lowPrice;
}
