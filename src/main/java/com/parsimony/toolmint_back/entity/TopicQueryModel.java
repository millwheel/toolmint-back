package com.parsimony.toolmint_back.entity;

import lombok.Data;

@Data
public class TopicQueryModel {

    private Long id;
    private String code;
    private String name;
    private String emoji;
    private Long productCount;

    public TopicQueryModel(Long id, String code, String name, String emoji, Long productCount) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.emoji = emoji;
        this.productCount = productCount;
    }

}
