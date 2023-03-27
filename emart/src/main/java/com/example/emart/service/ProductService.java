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

  public List<Product> getProductAll() {
    return productRepository.findAll();
  }

  public void addLike(Long productId) {

  }

  public Product findOne(Long productId) {
    return productRepository.findById(productId);
  }

  public List<Product> findProductInLikeProductByUserId(Long userId) {
    return productRepository.findProductInLikeProductByUserId(userId);
  }
}
