package org.example.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeResponseDTO {
    private String status;
    private String message;
}