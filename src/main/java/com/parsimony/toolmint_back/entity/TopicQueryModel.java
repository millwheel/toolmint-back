package com.parsimony.toolmint_back.entity;

import lombok.Data;

@Data
public class TopicQueryModel {

    private Long id;
    private String code;
    private String name;
    private String emoji;
    private Long productCount;

}
