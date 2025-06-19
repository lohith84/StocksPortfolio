package org.example.portfolio.dto;

import lombok.Data;

@Data
public class TradeRequestDTO {
    private Long userAccountId;
    private String tradeType;
    private Integer quantity;
    private Long stockId;

    public TradeRequestDTO() {
    }
}