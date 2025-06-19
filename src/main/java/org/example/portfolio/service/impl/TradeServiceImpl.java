package org.example.portfolio.service.impl;

import org.example.portfolio.dto.TradeRequestDTO;
import org.example.portfolio.dto.TradeResponseDTO;
import org.example.portfolio.entity.PortfolioEntity;
import org.example.portfolio.entity.StockEntity;
import org.example.portfolio.entity.TradeEntity;
import org.example.portfolio.repository.PortfolioRepository;
import org.example.portfolio.repository.StockRepository;
import org.example.portfolio.repository.TradeRepository;
import org.example.portfolio.service.TradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private StockRepository stockRepository;

    @Override
    public TradeResponseDTO executeTrade(TradeRequestDTO tradeRequest) {
        Optional<StockEntity> stockOptional = stockRepository.findById(tradeRequest.getStockId());
        if (stockOptional.isEmpty()) {
            return new TradeResponseDTO("Failure", "Stock not found");
        }

        StockEntity stock = stockOptional.get();
        Double tradePrice = stock.getClosePrice();

        if ("Buy".equalsIgnoreCase(tradeRequest.getTradeType())) {
            return handleBuyTrade(tradeRequest, tradePrice);
        }

        if ("Sell".equalsIgnoreCase(tradeRequest.getTradeType())) {
            return handleSellTrade(tradeRequest, tradePrice);
        }

        return new TradeResponseDTO("Failure", "Invalid trade type. Must be 'Buy' or 'Sell'");
    }

    private TradeResponseDTO handleBuyTrade(TradeRequestDTO tradeRequest, Double tradePrice) {
        TradeEntity trade = createTradeRecord(tradeRequest, tradePrice);

        PortfolioEntity portfolio = portfolioRepository.findByUserIdAndStockId(tradeRequest.getUserAccountId(), tradeRequest.getStockId());
        if (portfolio == null) {
            portfolio = new PortfolioEntity();
            portfolio.setUserId(tradeRequest.getUserAccountId());
            portfolio.setStockId(tradeRequest.getStockId());
            portfolio.setQuantity(tradeRequest.getQuantity());
            portfolio.setBuyPrice(tradePrice);
        } else {
            int newQuantity = portfolio.getQuantity() + tradeRequest.getQuantity();
            portfolio.setBuyPrice(((portfolio.getBuyPrice() * portfolio.getQuantity()) + (tradePrice * tradeRequest.getQuantity())) / newQuantity);
            portfolio.setQuantity(newQuantity);
        }

        portfolioRepository.save(portfolio);
        return new TradeResponseDTO("Success", "Trade executed successfully");
    }

    private TradeResponseDTO handleSellTrade(TradeRequestDTO tradeRequest, Double tradePrice) {
        PortfolioEntity portfolio = portfolioRepository.findByUserIdAndStockId(tradeRequest.getUserAccountId(), tradeRequest.getStockId());
        if (portfolio == null || portfolio.getQuantity() < tradeRequest.getQuantity()) {
            return new TradeResponseDTO("Failure", "Insufficient holdings for sell trade");
        }

        createTradeRecord(tradeRequest, tradePrice);

        portfolio.setQuantity(portfolio.getQuantity() - tradeRequest.getQuantity());
        if (portfolio.getQuantity() == 0) {
            portfolioRepository.delete(portfolio);
        } else {
            portfolioRepository.save(portfolio);
        }

        return new TradeResponseDTO("Success", "Trade executed successfully");
    }
    private TradeEntity createTradeRecord(TradeRequestDTO tradeRequest, Double tradePrice) {
        TradeEntity trade = new TradeEntity();
        trade.setUserAccountId(tradeRequest.getUserAccountId());
        trade.setTradeType(tradeRequest.getTradeType());
        trade.setStockId(tradeRequest.getStockId());
        trade.setQuantity(tradeRequest.getQuantity());
        trade.setPrice(tradePrice);

        return tradeRepository.save(trade);
    }
}