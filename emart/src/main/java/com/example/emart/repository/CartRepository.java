package com.example.emart.repository;

import com.example.emart.entity.Cart;
import com.example.emart.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

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

  public Optional<Cart> findCartByUserIdAndProductId(Long userId, Long productId) {
    return em.createQuery("select c from Cart c where c.user.id=:userId and c.product.id=:productId", Cart.class)
            .setParameter("userId", userId)
            .setParameter("productId", productId)
            .getResultStream().findFirst();
  }
}
