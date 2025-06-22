package com.parsimony.toolmint_back.dto.product;

import com.parsimony.toolmint_back.entity.Topic;
import lombok.Data;

@Data
public class ProductTopicResponse {

    private Long id;
    private String code;
    private String name;

    public ProductTopicResponse(Topic topic) {
        this.id = topic.getId();
        this.code = topic.getCode();
        this.name = topic.getName();
    }
}
