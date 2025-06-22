package com.parsimony.toolmint_back.entity;

import com.parsimony.toolmint_back.dto.product.ProductRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    private String summary;

    private String description;

    @Column(nullable = false)
    private String websiteUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TopicProductMapping> topicMappings = new HashSet<>();

    public Product(ProductRequest productRequest, Set<Topic> topics) {
        this.code = productRequest.getCode();
        this.name = productRequest.getName();
        this.summary = productRequest.getSummary();
        this.description = productRequest.getDescription();
        this.websiteUrl = productRequest.getWebsiteUrl();
        updateTopics(topics);
    }

    public void update(ProductRequest productRequest, Set<Topic> topics) {
        this.code = productRequest.getCode();
        this.name = productRequest.getName();
        this.summary = productRequest.getSummary();
        this.description = productRequest.getDescription();
        this.websiteUrl = productRequest.getWebsiteUrl();
        if (!topics.isEmpty()) {
            updateTopics(topics);
        }
    }

    public void updateTopics(Set<Topic> newTopics) {
        topicMappings.clear();
        for (Topic topic : newTopics) {
            topicMappings.add(new TopicProductMapping(this, topic));
        }
    }

}