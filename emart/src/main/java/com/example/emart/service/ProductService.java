package com.example.emart.service;

import com.example.emart.config.auth.PrincipalDetails;
import com.example.emart.entity.Order;
import com.example.emart.entity.OrderProduct;
import com.example.emart.entity.Product;
import com.example.emart.entity.User;
import com.example.emart.repository.OrderProductRepository;
import com.example.emart.repository.OrderRepository;
import com.example.emart.repository.ProductRepository;
import com.example.emart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final OrderProductService orderProductService;
  private final OrderRepository orderRepository;
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

  public Product getProductInfoById(Long productId) {
    return productRepository.findById(productId);
  }
  @Transactional
  public void cancelOrder(Long userId, Long orderId, OrderProduct orderProduct1) {
    User findUser = userRepository.getUserInfoById(userId).orElseThrow(() -> new UsernameNotFoundException(""));
    Order findOrder = orderRepository.findById(orderId);
    List<OrderProduct> orderProducts = orderProductService.findAllWithProduct(orderId);
    for (OrderProduct orderProduct : orderProducts) {
      findOrder.cancel();
      findOrder.getOrderProducts().remove(orderProduct);
      orderProduct.getProduct().getOrderProducts().remove(orderProduct);
      orderProductService.delete(orderProduct);
    }
    orderRepository.delete(findOrder);
  }
}
