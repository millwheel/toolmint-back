package com.parsimony.toolmint_back.dto.product;

import com.parsimony.toolmint_back.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductSummaryResponse {

    private Long id;
    private String code;
    private String name;
    private String summary;
    private List<ProductTopicResponse> topics;

    public ProductSummaryResponse(Product product) {
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.summary = product.getSummary();
        this.topics = product.getTopicMappings().stream()
                .map(mapping -> new ProductTopicResponse(mapping.getTopic()))
                .toList();
    }
}
