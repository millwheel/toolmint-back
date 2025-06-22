package com.parsimony.toolmint_back.service;

import com.parsimony.toolmint_back.dto.product.ProductRequest;
import com.parsimony.toolmint_back.entity.Product;
import com.parsimony.toolmint_back.entity.ProductViewStatistic;
import com.parsimony.toolmint_back.entity.Topic;
import com.parsimony.toolmint_back.exception.custom.DataNotFoundException;
import com.parsimony.toolmint_back.exception.custom.InvalidInputException;
import com.parsimony.toolmint_back.repository.ProductRepository;
import com.parsimony.toolmint_back.repository.ProductViewStatisticRepository;
import com.parsimony.toolmint_back.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final TopicRepository topicRepository;
    private final ProductRepository productRepository;
    private final ProductViewStatisticRepository productViewStatisticRepository;

    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("product", "id", id));
    }

    @Transactional
    public void createProduct(ProductRequest productRequest) {
        List<Long> topicIds = productRequest.getTopicIds();
        if (topicIds == null || topicIds.isEmpty()) {
            throw new InvalidInputException("topic ids should not be empty");
        }

        Set<Topic> topics = topicRepository.findByIdIn(topicIds);
        Product product = new Product(productRequest, topics);
        Product savedProduct = productRepository.save(product);

        ProductViewStatistic statistic = new ProductViewStatistic(savedProduct);
        productViewStatisticRepository.save(statistic);
    }

    @Transactional
    public void updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("product", "id", id));
        List<Long> topicIds = productRequest.getTopicIds();
        Set<Topic> topics = topicIds == null || topicIds.isEmpty() ? Set.of() : topicRepository.findByIdIn(topicIds);

        product.update(productRequest, topics);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("product", "id", id));

        ProductViewStatistic statistic = productViewStatisticRepository.findByProduct(product)
                .orElseThrow(() -> new DataNotFoundException("product view statistic", "product id", product.getCode()));

        productViewStatisticRepository.delete(statistic);
        productRepository.delete(product);
    }
}