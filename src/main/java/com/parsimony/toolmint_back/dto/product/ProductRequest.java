package com.parsimony.toolmint_back.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {

    private String code;
    private String name;
    private String summary;
    private String description;
    private String websiteUrl;
    private List<Long> topicIds;

}
