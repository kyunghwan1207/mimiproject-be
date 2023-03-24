package com.example.emart.controller;

import com.example.emart.entity.Product;
import com.example.emart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
  private final ProductService productService;

  // 상품 전체 리스트 조회
  @GetMapping("")
  public List<Product> getAllProductsList() {
    return productService.getAllProductsList();
  }

  // 특정 상품 상세보기
  @GetMapping("/{id}")
  public Product getProductDetail(@PathVariable Long id) {
    return productService.getProductDetail(id);
  }

  // 상품명 검색 결과 리스트 조회
  @GetMapping("/search")
  public List<Product> getProductSearchList(@RequestParam String q) {
    return productService.getProductSearchList(q);
  }
}