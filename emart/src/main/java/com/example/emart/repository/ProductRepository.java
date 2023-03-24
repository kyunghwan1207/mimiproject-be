package com.example.emart.repository;

import com.example.emart.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class ProductRepository {
  private final EntityManager em;

  public Product findById(Long id) {
    return em.find(Product.class, id);
  }

  public List<Product> getAllProductsList() {
    return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
  }

  public List<Product> getProductSearchList(String searchWord) {
    return em.createQuery("SELECT p FROM Product p WHERE p.name like :name", Product.class)
        .setParameter("name", "%" + searchWord + "%")
        .getResultList();
  }

  public Product save(Product product) {
    em.persist(product);
    return product;
  }
}