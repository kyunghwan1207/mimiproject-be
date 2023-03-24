package com.example.emart;

import com.example.emart.repository.CartRepository;
import com.example.emart.repository.EventRepository;
import com.example.emart.repository.ProductRepository;
import com.example.emart.repository.UserRepository;
import com.example.emart.service.CartService;
import com.example.emart.service.EventService;
import com.example.emart.service.ProductService;
import com.example.emart.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {
//  private final EntityManager em;
//
//  public SpringConfig(EntityManager em) {
//    this.em = em;
//  }
//
//  @Bean
//  public ProductService productService() {
//    return new ProductService(productRepository());
//  }
//
//  @Bean
//  public ProductRepository productRepository() {
//    return new ProductRepository(em);
//  }
//
//
////  @Bean
//  public UserService userService() {
//    return new UserService(userRepository());
//  }
//
////  @Bean
//  public UserRepository userRepository() {
//    return new UserRepository(em);
//  }
//
//
////  @Bean
//  public CartService cartService() {
//    return new CartService(cartRepository(), productRepository(), userRepository());
//  }
//
////  @Bean
//  public CartRepository cartRepository() {
//    return new CartRepository(em);
//  }
//
////  @Bean
//  public EventService eventService() {
//    return new EventService(eventRepository());
//  }
//
////  @Bean
//  public EventRepository eventRepository() {
//    return new EventRepository(em);
//  }
}
