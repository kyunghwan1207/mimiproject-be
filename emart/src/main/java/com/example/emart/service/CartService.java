package com.example.emart.service;

import com.example.emart.dto.CartAddRequestDTO;
import com.example.emart.entity.Cart;
import com.example.emart.entity.Product;
import com.example.emart.repository.CartRepository;
import com.example.emart.repository.ProductRepository;
import com.example.emart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  @Transactional
  public Cart addProduct(CartAddRequestDTO cartDTO) {
    Optional<Cart> sameProduct = userRepository.getSameProduct(cartDTO);
    Cart cart;
    if (sameProduct.isEmpty()) {
      cart = new Cart(
      );
      cart.setUser(userRepository.getUserInfoById(cartDTO.getUserId()).get());
      cart.setProduct(productRepository.findById(cartDTO.getProductId()));
      cart.setQty(cartDTO.getQty());
    } else {
      cart = sameProduct.get();
      cart.setQty(cart.getQty() + 1);
    }
    return cartRepository.save(cart);
  }

  public List<Product> getAllCartProductList(Long userId) {
    return cartRepository.getAllCartProductList(userId);
  }

  @Transactional
  public Cart changeCartQty(int qty, Long id) {
    Cart cart = cartRepository.getCartInfoById(id);
    if (cart.getQty() + qty <= 0) {
      cart.setQty(1);
    } else {
      cart.setQty(cart.getQty() + qty);
    }
    return cart;
  }

  @Transactional
  public void deleteCartProduct(Long id) {
    Cart cart = cartRepository.getCartInfoById(id);
    cartRepository.deleteCartProduct(cart);
  }
}
