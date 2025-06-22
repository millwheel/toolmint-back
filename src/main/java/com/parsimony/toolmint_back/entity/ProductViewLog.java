package com.parsimony.toolmint_back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductViewLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String productName;

    private String userId;

    private String ipAddress;

    private String userAgent;

    public ProductViewLog(Product product, String userId, String ipAddress, String userAgent) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }
}
