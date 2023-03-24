package com.example.emart.controller;

import com.example.emart.dto.ProductResponseDto;
import com.example.emart.entity.Product;
import com.example.emart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
  private final ProductService productService;

  // 상품 전체 리스트 조회
  @GetMapping("")
  public ResponseEntity getAllProductsList() {
    List<Product> productList = productService.getAllProductsList();
    List<ProductResponseDto> productResponseDtoList = productList.stream().map(p -> new ProductResponseDto(p)).collect(Collectors.toList());
    return new ResponseEntity(productResponseDtoList, HttpStatus.ACCEPTED); // 202
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