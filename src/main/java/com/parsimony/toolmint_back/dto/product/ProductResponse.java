package com.parsimony.toolmint_back.dto.product;

import com.parsimony.toolmint_back.entity.Product;
import com.parsimony.toolmint_back.entity.ProductViewStatistic;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private Long id;
    private String code;
    private String name;
    private String summary;
    private String description;
    private String websiteUrl;
    private int totalViews;
    private List<ProductTopicResponse> topics;

    public ProductResponse(Product product, ProductViewStatistic stats) {
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.summary = product.getSummary();
        this.description = product.getDescription();
        this.websiteUrl = product.getWebsiteUrl();
        this.totalViews = stats.getTotalViews();
        this.topics = product.getTopicMappings().stream()
                .map(mapping -> new ProductTopicResponse(mapping.getTopic()))
                .toList();
    }

}
