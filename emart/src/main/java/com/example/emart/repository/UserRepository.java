package com.example.emart.repository;

import com.example.emart.dto.CartAddRequestDTO;
import com.example.emart.entity.Cart;
import com.example.emart.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class UserRepository {
  private final EntityManager em;

  public void save(User user) {
    em.persist(user);
  }

  public Optional<User> getUserInfoById(Long id) {
    return Optional.ofNullable(em.find(User.class, id));
  }

  public Optional<User> getUserInfoByEmail(String email) {
    return em.createQuery("SELECT u FROM User u WHERE u.email=:email", User.class)
        .setParameter("email", email)
        .getResultList()
        .stream()
        .findAny();
  }

  public Optional<Cart> getSameProduct(CartAddRequestDTO cartDTO) {
    return em.createQuery("SELECT c FROM Cart c WHERE c.product.id=:productId and c.user.id=:userId", Cart.class)
        .setParameter("productId", cartDTO.getProductId())
        .setParameter("userId", cartDTO.getUserId())
        .getResultStream().findFirst();
  }
}
