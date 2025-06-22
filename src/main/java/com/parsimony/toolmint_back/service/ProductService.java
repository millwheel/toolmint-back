package com.parsimony.toolmint_back.service;

import com.parsimony.toolmint_back.dto.product.ProductRequest;
import com.parsimony.toolmint_back.entity.Product;
import com.parsimony.toolmint_back.entity.ProductViewStatistic;
import com.parsimony.toolmint_back.exception.custom.DataNotFoundException;
import com.parsimony.toolmint_back.exception.custom.InvalidInputException;
import com.parsimony.toolmint_back.repository.ProductRepository;
import com.parsimony.toolmint_back.repository.ProductViewStatisticRepository;
import com.parsimony.toolmint_back.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final TopicRepository topicRepository;
    private final ProductRepository productRepository;
    private final ProductViewStatisticRepository productViewStatisticRepository;

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

        var topics = topicRepository.findByIdIn(topicIds);
        var product = new Product(productRequest, topics);
        productRepository.save(product);

        var statistic = new ProductViewStatistic(product);
        productViewStatisticRepository.save(statistic);
    }

    @Transactional
    public void updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("product", "id", id));
        product.update(productRequest);
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