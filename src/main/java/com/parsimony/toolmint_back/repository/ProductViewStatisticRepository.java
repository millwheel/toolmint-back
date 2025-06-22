package com.parsimony.toolmint_back.repository;

import com.parsimony.toolmint_back.entity.Product;
import com.parsimony.toolmint_back.entity.ProductViewStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductViewStatisticRepository extends JpaRepository<ProductViewStatistic, Long> {

    Optional<ProductViewStatistic> findByProduct(Product product);

    // 최근 7일간 인기 상품 TOP 3
    @Query("""
        SELECT p FROM ProductViewStatistic p
        ORDER BY p.last7DaysViews DESC
        LIMIT 3
    """)
    List<ProductViewStatistic> findTop3ByLast7Days();

    // 최근 30일간 인기 상품 TOP 3
    @Query("""
        SELECT p FROM ProductViewStatistic p
        ORDER BY p.last30DaysViews DESC
        LIMIT 3
    """)
    List<ProductViewStatistic> findTop3ByLast30Days();
}