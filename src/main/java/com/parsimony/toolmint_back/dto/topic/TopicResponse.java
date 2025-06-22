package com.parsimony.toolmint_back.dto.topic;

import com.parsimony.toolmint_back.dto.product.TopicProductResponse;
import com.parsimony.toolmint_back.entity.Topic;
import lombok.Data;

import java.util.List;

@Data
public class TopicResponse {

    private Long id;
    private String code;
    private String name;
    private String emoji;
    private List<TopicProductResponse> products;

    public TopicResponse(Topic topic) {
        this.id = topic.getId();
        this.code = topic.getCode();
        this.name = topic.getName();
        this.emoji = topic.getEmoji();
        this.products = topic.getProducts().stream()
                .map(TopicProductResponse::new).toList();
    }
}
