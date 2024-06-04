package com.yapily.bart.exercise.controller;

import com.yapily.bart.exercise.mapper.ProductMapper;
import com.yapily.bart.exercise.model.Product;
import com.yapily.bart.exercise.model.dto.ProductDTO;
import com.yapily.bart.exercise.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("")
    ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<Product> allProducts = productService.findAllProducts();
            return allProducts.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.OK).body(allProducts.stream().map(productMapper::convertProductEntityToDTO).collect(toList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductDTO> getAllProducts(@PathVariable("productId") Long productId) {
        try {
            Optional<Product> product = productService.findProductByProductId(productId);
            return product.map(value -> ResponseEntity.status(HttpStatus.OK).body(productMapper.convertProductEntityToDTO(value))).orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{productId}")
    ResponseEntity<ProductDTO> createNewProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product product = productMapper.convertProductDTOtoEntity(productDTO);
            Optional<Product> persistedProduct = productService.createNewProduct(product);
            return persistedProduct.map(value -> ResponseEntity.status(201).body(productMapper.convertProductEntityToDTO(value))).orElseGet(() -> ResponseEntity.unprocessableEntity().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<HttpStatus> deleteProduct(@PathVariable("productId") Long productId) {
        try {
            int success = productService.deleteProductByProductId(productId);
            return success == 1 ? ResponseEntity.status(204).build() : ResponseEntity.unprocessableEntity().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
