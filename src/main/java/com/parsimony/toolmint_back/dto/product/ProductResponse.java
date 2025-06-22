package com.parsimony.toolmint_back.dto.product;

import com.parsimony.toolmint_back.entity.Product;
import lombok.Data;

@Data
public class ProductResponse {

    private Long id;
    private String code;
    private String name;
    private String summary;
    private String description;
    private String websiteUrl;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.summary = product.getSummary();
        this.description = product.getDescription();
        this.websiteUrl = product.getWebsiteUrl();
    }

}
