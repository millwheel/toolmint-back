package com.parsimony.toolmint_back.entity;

import com.parsimony.toolmint_back.dto.topic.TopicRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Topic extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 10)
    private String emoji;

    @ManyToMany(mappedBy = "topics", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    public Topic(TopicRequest request) {
        this.code = request.getCode();
        this.name = request.getName();
        this.emoji = request.getEmoji();
    }

    public void update(TopicRequest request) {
        this.code = request.getCode();
        this.name = request.getName();
        this.emoji = request.getEmoji();
    }

}