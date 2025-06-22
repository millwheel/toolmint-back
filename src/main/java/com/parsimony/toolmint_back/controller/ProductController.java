package com.parsimony.toolmint_back.controller;


import com.parsimony.toolmint_back.dto.product.ProductRequest;
import com.parsimony.toolmint_back.dto.product.ProductResponse;
import com.parsimony.toolmint_back.dto.product.ProductSummaryResponse;
import com.parsimony.toolmint_back.entity.Product;
import com.parsimony.toolmint_back.entity.ProductViewStatistic;
import com.parsimony.toolmint_back.service.ProductService;
import com.parsimony.toolmint_back.service.ProductViewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.parsimony.toolmint_back.util.UserRequestUtils.getClientIpAddress;
import static com.parsimony.toolmint_back.util.UserRequestUtils.getUserAgent;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductViewService productViewService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductSummaryResponse> getProducts(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productService.getProducts(page, size);
        return products.stream()
                .map(ProductSummaryResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProduct(@PathVariable Long id,
                                      @RequestAttribute(name = "userId", required = false) String userId,
                                      HttpServletRequest request) {

        String ipAddress = getClientIpAddress(request);
        String userAgent = getUserAgent(request);
        Product product = productService.getProduct(id);
        productViewService.saveView(product, userId, ipAddress, userAgent);
        ProductViewStatistic stats = productViewService.getProductViewStatistic(product);

        return new ProductResponse(product, stats);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@Valid @RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable Long id,
                              @RequestBody ProductRequest productRequest) {
        productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}