package com.example.emart.repository;

import com.example.emart.entity.LikeProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeProductRepository {
    private final EntityManager em;

    public LikeProduct save(LikeProduct likeProduct) {
        em.persist(likeProduct);
        return likeProduct;
    }

    public List<LikeProduct> findLikeProductWithProduct() {
        return em.createQuery("select lp from LikeProduct lp join fetch lp.product p", LikeProduct.class)
                .getResultList();
    }

    public List<LikeProduct> findLikeProductWithProductByUserId(Long userId) {
        return em.createQuery("select lp from LikeProduct lp join fetch lp.product p where lp.user.id=:userId", LikeProduct.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void delete(LikeProduct likeProduct) {
        System.out.println("likeProduct = " + likeProduct);
        System.out.println("likeProduct.getId() = " + likeProduct.getId());
        em.createQuery("delete from LikeProduct lp where lp.id=:likeProductId")
                .setParameter("likeProductId", likeProduct.getId())
                .executeUpdate();
                
    }
}
