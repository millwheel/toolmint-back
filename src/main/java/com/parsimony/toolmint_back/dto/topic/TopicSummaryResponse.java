package com.parsimony.toolmint_back.dto.topic;

import com.parsimony.toolmint_back.entity.TopicQueryModel;
import lombok.Data;

@Data
public class TopicSummaryResponse {

    private Long id;
    private String code;
    private String name;
    private Long productCount;

    public TopicSummaryResponse(TopicQueryModel topicQueryModel) {
        this.id = topicQueryModel.getId();
        this.code = topicQueryModel.getCode();
        this.name = topicQueryModel.getName();
        this.productCount = topicQueryModel.getProductCount();
    }
}
