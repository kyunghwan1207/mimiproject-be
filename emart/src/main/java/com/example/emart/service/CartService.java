package com.example.emart.service;

import com.example.emart.dto.CartAddRequestDTO;
import com.example.emart.dto.CartProductDto;
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


  public List<CartProductDto> getAllCartProductList(Long userId) {
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

  public void save(Cart cart) {
    cartRepository.save(cart);
  }

  public Optional<Cart> findCart(Long userId, Product product) {
    return cartRepository.findCartByUserIdAndProductId(userId, product.getId());
  }

  @Transactional
  public void updateQty(Cart cart, int newQty) {
    cart.setQty(newQty);

  }
  public int findCartCountWithUserId(Long userId) {
    return cartRepository.findCarCountWithUserId(userId);
  }
}
