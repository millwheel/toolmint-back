package com.parsimony.toolmint_back.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product_view_statistic")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductViewStatistic extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int totalViews = 0;

    private int last7DaysViews = 0;

    private int last30DaysViews = 0;

    public ProductViewStatistic(Product product) {
        this.product = product;
    }

    public void incrementTotalViews() {
        this.totalViews++;
    }

    public void updatePeriodViews(int last7Days, int last30Days) {
        this.last7DaysViews = last7Days;
        this.last30DaysViews = last30Days;
    }

}