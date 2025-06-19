package org.example.portfolio.service.impl;

import org.example.portfolio.dto.PortfolioResponseDTO;
import org.example.portfolio.dto.HoldingDTO;
import org.example.portfolio.entity.PortfolioEntity;
import org.example.portfolio.entity.TradeEntity;
import org.example.portfolio.entity.StockEntity;
import org.example.portfolio.repository.PortfolioRepository;
import org.example.portfolio.repository.StockRepository;
import org.example.portfolio.repository.TradeRepository;
import org.example.portfolio.service.PortfolioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public PortfolioResponseDTO getUserPortfolio(Long userId) {
        List<TradeEntity> trades = tradeRepository.findByUserAccountId(userId);

        Map<Long, HoldingDTO> holdingsMap = new HashMap<>();

        for (TradeEntity trade : trades) {
            StockEntity stock = stockRepository.findByStockId(trade.getStockId());
            Double currentPrice = stock.getClosePrice();
            holdingsMap.compute(trade.getStockId(), (stockId, existingHolding) -> {
                if (existingHolding != null) {
                    int newQuantity = "Buy".equalsIgnoreCase(trade.getTradeType())
                            ? existingHolding.getQuantity() + trade.getQuantity()
                            : existingHolding.getQuantity() - trade.getQuantity();
                    if (newQuantity <= 0) return null;

                    double newBuyPrice = ((existingHolding.getBuyPrice() * existingHolding.getQuantity())
                            + ("Buy".equalsIgnoreCase(trade.getTradeType()) ? trade.getPrice() * trade.getQuantity() : 0))
                            / newQuantity;

                    double totalValue = currentPrice * newQuantity;
                    double gainLoss = totalValue - (newBuyPrice * newQuantity);

                    existingHolding.setQuantity(newQuantity);
                    existingHolding.setBuyPrice(newBuyPrice);
                    existingHolding.setGainLoss(gainLoss);
                    existingHolding.setTotalValue(totalValue);
                    return existingHolding;
                } else if ("Buy".equalsIgnoreCase(trade.getTradeType())) {
                    double totalValue = currentPrice * trade.getQuantity();
                    double gainLoss = totalValue - (trade.getPrice() * trade.getQuantity());

                    return new HoldingDTO(
                            stock.getStockName(),
                            stock.getStockId(),
                            trade.getQuantity(),
                            trade.getPrice(),
                            currentPrice,
                            gainLoss,
                            totalValue
                    );
                } else {
                    return null;
                }
            });
        }

        List<HoldingDTO> holdings = new ArrayList<>(holdingsMap.values());
        double totalBuyPrice = holdings.stream().mapToDouble(h -> h.getBuyPrice() * h.getQuantity()).sum();
        double totalCurrentValue = holdings.stream().mapToDouble(HoldingDTO::getTotalValue).sum();
        double totalPL = totalCurrentValue - totalBuyPrice;
        double totalPLPercentage = (totalBuyPrice == 0) ? 0 : (totalPL / totalBuyPrice) * 100;

        return new PortfolioResponseDTO(holdings, totalBuyPrice, totalCurrentValue, totalPL, totalPLPercentage);
    }

    @Override
    public List<PortfolioResponseDTO> getAllUserPortfolios(){
        List<Long> userIds = portfolioRepository.findAllUserIds();
        return userIds.stream().map(this::getUserPortfolio).collect(Collectors.toList());
    }
}