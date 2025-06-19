package org.example.portfolio.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Data
@Table(name = "trade")
public class TradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userAccountId;
    private Long stockId;
    private String tradeType;
    private Integer quantity;
    private Double price;

//    @ManyToOne
//    @JoinColumn(name = "portfolio_id", nullable = true)
//    private PortfolioEntity portfolio;
}