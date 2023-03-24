package com.example.emart.repository;

import com.example.emart.entity.Cart;
import com.example.emart.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class CartRepository {
  private final EntityManager em;

  public Cart save(Cart cart) {
    em.persist(cart);
    return cart;
  }

  public List<Product> getAllCartProductList(Long userId) {
    return em.createQuery("select c.product from Cart c where c.user.id=:userId", Product.class)
        .setParameter("userId", userId)
        .getResultList();
  }

  public Cart getCartInfoById(Long id) {
    return em.find(Cart.class, id);
  }

  public void deleteCartProduct(Cart cart) {
    em.remove(cart);
  }
}
