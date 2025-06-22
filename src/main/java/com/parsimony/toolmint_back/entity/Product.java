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

    @ManyToMany
    @JoinTable(
            name = "product_topic",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private Set<Topic> topics = new HashSet<>();

    public Product(ProductRequest productRequest, Set<Topic> topics) {
        this.code = productRequest.getCode();
        this.name = productRequest.getName();
        this.summary = productRequest.getSummary();
        this.description = productRequest.getDescription();
        this.websiteUrl = productRequest.getWebsiteUrl();
        this.topics = topics;
        updateTopics(topics);
    }

    public void update(ProductRequest productRequest) {
        this.code = productRequest.getCode();
        this.name = productRequest.getName();
        this.summary = productRequest.getSummary();
        this.description = productRequest.getDescription();
        this.websiteUrl = productRequest.getWebsiteUrl();
    }

    public void updateTopics(Set<Topic> newTopics) {
        for (Topic topic : this.topics) {
            topic.getProducts().remove(this);
        }

        this.topics.clear();

        for (Topic topic : newTopics) {
            this.topics.add(topic);
            topic.getProducts().add(this); // 양방향 연관관계 유지
        }
    }

}