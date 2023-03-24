package com.example.emart.repository;

import com.example.emart.dto.CartProductDto;
import com.example.emart.entity.Cart;
import com.example.emart.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
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

  public List<CartProductDto> getAllCartProductList(Long userId) {
    List<Object[]> resultList = em.createQuery("select c.product, c.qty from Cart c where c.user.id=:userId")
            .setParameter("userId", userId)
            .getResultList();
    List<CartProductDto> cartProductDtos = new ArrayList<>();
    for (Object[] result : resultList) {
      System.out.println("result[0] = " + result[0]);
      System.out.println("result[1] = " + result[1]);
      cartProductDtos.add(new CartProductDto((Product) result[0], (int) result[1]));
    }
    return cartProductDtos;
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

  public int findCarCountWithUserId(Long userId) {
    Long res = em.createQuery("select count(c) from Cart c where c.user.id=:userId", Long.class)
            .setParameter("userId", userId)
            .getSingleResult();
    if (res != null) {
      int ret = Integer.parseInt(res.toString());
      return ret;
    }
    return Integer.valueOf(0);
  }
}
