package com.parsimony.toolmint_back.service;

import com.parsimony.toolmint_back.entity.Product;
import com.parsimony.toolmint_back.entity.ProductViewLog;
import com.parsimony.toolmint_back.entity.ProductViewStatistic;
import com.parsimony.toolmint_back.exception.custom.DataNotFoundException;
import com.parsimony.toolmint_back.repository.ProductRepository;
import com.parsimony.toolmint_back.repository.ProductViewLogRepository;
import com.parsimony.toolmint_back.repository.ProductViewStatisticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductViewService {

    private final ProductRepository productRepository;
    private final ProductViewLogRepository productViewLogRepository;
    private final ProductViewStatisticRepository productViewStatisticRepository;

    public ProductViewStatistic getProductViewStatistic(Product product) {
        return productViewStatisticRepository.findByProduct(product)
                .orElseThrow(() -> new DataNotFoundException("view statistic", "product id", product.getId()));
    }

    public List<Product> getTop3ByLast7Days() {
        return productViewStatisticRepository.findTop3ByLast7Days()
                .stream()
                .map(ProductViewStatistic::getProduct)
                .toList();
    }

    public List<Product> getTop3ByLast30Days() {
        return productViewStatisticRepository.findTop3ByLast30Days()
                .stream()
                .map(ProductViewStatistic::getProduct)
                .toList();
    }

    @Async
    @Transactional
    public void saveView(Product product, String userId, String ipAddress, String userAgent) {
        try {
            ProductViewLog viewLog = new ProductViewLog(product, userId, ipAddress, userAgent);
            productViewLogRepository.save(viewLog);

            ProductViewStatistic productViewStatistic = getProductViewStatistic(product);
            productViewStatistic.incrementTotalViews();

        } catch (Exception e) {
            log.error("Failed to update view", e);
        }
    }

    @Scheduled(cron = "0 0 1 * * *") // 매일 새벽 1시
    @Transactional
    public void updatePeriodViewStats() {
        log.info("Starting to update period view stats");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last7DaysBefore = now.minusDays(7);
        LocalDateTime last30DaysBefore = now.minusDays(30);

        try {
            List<Product> allProducts = productRepository.findAll();

            for (Product product : allProducts) {
                int last7DaysCount = productViewLogRepository.countByProductIdAndCreatedAtAfter(product.getId(), last7DaysBefore);
                int last30DaysCount = productViewLogRepository.countByProductIdAndCreatedAtAfter(product.getId(), last30DaysBefore);

                ProductViewStatistic stats = productViewStatisticRepository.findByProduct(product)
                        .orElse(new ProductViewStatistic(product));

                stats.updatePeriodViews(last7DaysCount, last30DaysCount);
            }

            log.info("Successfully updated period view stats for {} products", allProducts.size());
        } catch (Exception e) {
            log.error("Failed to update period view statistic", e);
        }
    }
}
