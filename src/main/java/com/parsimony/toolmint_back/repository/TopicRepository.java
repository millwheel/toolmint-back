package com.parsimony.toolmint_back.repository;

import com.parsimony.toolmint_back.entity.Topic;
import com.parsimony.toolmint_back.entity.TopicQueryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("""
        SELECT new com.parsimony.toolmint_back.entity.TopicQueryModel(
            t.id, t.code, t.name, t.emoji, COUNT(p)
        )
        FROM Topic t
        LEFT JOIN t.products p
        GROUP BY t.id, t.code, t.name, t.emoji
    """)
    List<TopicQueryModel> findAllQueryModel();

    Set<Topic> findByIdIn(List<Long> ids);

    boolean existsByCode(String code);

    boolean existsByName(String name);

}
