package org.example.portfolio.controller;

import org.example.portfolio.entity.StockEntity;
import org.example.portfolio.repository.StockRepository;
import org.example.portfolio.service.StockSearchService;
import org.example.portfolio.service.StockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stock-details")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping(value = "/{stockId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockEntity> getStockDetails(@PathVariable("stockId") Long stockId){
        try{
            StockEntity stockEntity = stockService.getStockById(stockId);
            return stockEntity == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(stockEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).build();
        }
    }

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/all")
    public ResponseEntity<List<StockEntity>> getAllStocks() {
        List<StockEntity> stocks = stockRepository.findAll();
        return ResponseEntity.ok(stocks);
    }

    @Autowired
    private StockSearchService stockSearchService;

    @GetMapping("/search")
    public ResponseEntity<?> searchStocks(@RequestParam(name = "query") String query) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Query parameter cannot be null or empty");
        }
        List<StockEntity> stocks = stockSearchService.searchStocksByName(query.trim());
        return ResponseEntity.ok(stocks);
    }
}

