package com.parsimony.toolmint_back.controller;

import com.parsimony.toolmint_back.dto.topic.TopicRequest;
import com.parsimony.toolmint_back.dto.topic.TopicResponse;
import com.parsimony.toolmint_back.dto.topic.TopicSummaryResponse;
import com.parsimony.toolmint_back.entity.Topic;
import com.parsimony.toolmint_back.entity.TopicQueryModel;
import com.parsimony.toolmint_back.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TopicSummaryResponse> getTopics() {
        List<TopicQueryModel> topics = topicService.getTopics();
        return topics.stream()
                .map(TopicSummaryResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TopicResponse getTopic(@PathVariable Long id) {
        Topic topic = topicService.getTopic(id);
        return new TopicResponse(topic);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTopic(@Valid @RequestBody TopicRequest request) {
        topicService.createTopic(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTopic(@PathVariable Long id, @Valid @RequestBody TopicRequest request) {
        Topic topic = topicService.getTopic(id);
        topicService.updateTopic(topic, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long id) {
        Topic topic = topicService.getTopic(id);
        topicService.deleteTopic(topic);
    }
}