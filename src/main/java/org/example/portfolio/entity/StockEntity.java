package org.example.portfolio.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Data
@Table(name = "stocks")
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stockId;
    private String stockName;
    private Double openPrice;
    private Double closePrice;
    private Double highPrice;
    private Double lowPrice;
}