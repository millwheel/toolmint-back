package com.parsimony.toolmint_back.service;

import com.parsimony.toolmint_back.dto.topic.TopicRequest;
import com.parsimony.toolmint_back.entity.Topic;
import com.parsimony.toolmint_back.entity.TopicQueryModel;
import com.parsimony.toolmint_back.exception.custom.ConflictException;
import com.parsimony.toolmint_back.exception.custom.DataNotFoundException;
import com.parsimony.toolmint_back.exception.custom.InvalidInputException;
import com.parsimony.toolmint_back.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicService {

    private final TopicRepository topicRepository;

    public List<TopicQueryModel> getTopics() {
        return topicRepository.findAllQueryModel();
    }

    public Topic getTopic(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("topic", "id", id));
    }

    @Transactional
    public void createTopic(TopicRequest topicRequest) {
        validateRequest(topicRequest);
        Topic topic = new Topic(topicRequest);
        topicRepository.save(topic);
    }

    @Transactional
    public void updateTopic(Topic topic, TopicRequest topicRequest) {
        validateRequest(topicRequest);
        topic.update(topicRequest);
    }

    private void validateRequest(TopicRequest topicRequest) {
        if (topicRequest.getCode().startsWith("/")) {
            throw new InvalidInputException("code should start without slash(/)");
        }
        if (topicRepository.existsByCode(topicRequest.getCode())) {
            throw new ConflictException("topic code already used");
        }
        if (topicRepository.existsByName(topicRequest.getName())) {
            throw new ConflictException("topic name already used");
        }
    }

    @Transactional
    public void deleteTopic(Topic topic) {
        // TODO topic이 하나밖에 없는 product가 있는 경우 해당 topic을 삭제하지 못하도록 로직 추가
        topicRepository.delete(topic);
    }
}
