package com.parsimony.toolmint_back.dto.product;

import com.parsimony.toolmint_back.entity.Product;
import com.parsimony.toolmint_back.entity.ProductViewStatistic;
import lombok.Data;

@Data
public class ProductResponse {

    private Long id;
    private String code;
    private String name;
    private String summary;
    private String description;
    private String websiteUrl;
    private int totalViews;

    public ProductResponse(Product product, ProductViewStatistic stats) {
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.summary = product.getSummary();
        this.description = product.getDescription();
        this.websiteUrl = product.getWebsiteUrl();
        this.totalViews = stats.getTotalViews();
    }

}
