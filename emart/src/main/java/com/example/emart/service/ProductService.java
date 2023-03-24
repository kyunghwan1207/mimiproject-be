package com.example.emart.service;

import com.example.emart.entity.Product;
import com.example.emart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  public List<Product> getAllProductsList() {
    return productRepository.getAllProductsList();
  }

  public Product getProductDetail(Long id) {
    return productRepository.findById(id);
  }

  public List<Product> getProductSearchList(String searchWord) {
    return productRepository.getProductSearchList(searchWord);
  }

  @Transactional
  public Product addProduct(Product product){
    return productRepository.save(product);
  }

  public Product getProductWithInitializedCarts(Long productId) {
    Product findProduct = productRepository.findById(productId);
    System.out.println("getProductWithInitializedCarts / findProduct = " + findProduct);
    findProduct.getCarts().stream().forEach(c -> c.getId());
    System.out.println("after stream.forEach() / findProduct = " + findProduct);
    return findProduct;
  }
}
