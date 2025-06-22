package com.parsimony.toolmint_back.repository;

import com.parsimony.toolmint_back.entity.ProductViewLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ProductViewLogRepository extends JpaRepository<ProductViewLog, Long> {

    int countByProductIdAndCreatedAtAfter(Long productId, LocalDateTime startDateTime);
}
