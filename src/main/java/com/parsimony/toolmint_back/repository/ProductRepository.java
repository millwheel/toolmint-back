package com.parsimony.toolmint_back.repository;

import com.parsimony.toolmint_back.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
