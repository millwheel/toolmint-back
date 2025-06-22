package com.parsimony.toolmint_back.dto.product;

import com.parsimony.toolmint_back.entity.Product;
import lombok.Data;

@Data
public class TopicProductResponse {

    private Long id;
    private String code;
    private String name;
    private String summary;

    public TopicProductResponse(Product product) {
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.summary = product.getSummary();
    }
}
