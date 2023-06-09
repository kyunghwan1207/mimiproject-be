package com.example.emart.repository;

import com.example.emart.entity.LikeProduct;
import com.example.emart.entity.OrderProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderProductRepository {
    private final EntityManager em;
    public List<OrderProduct> findAllWithProduct(Long orderId) {
        return em.createQuery("select op from OrderProduct op join fetch op.product p where op.order.id=:orderId order by op.order.createdAt DESC", OrderProduct.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
    public void delete(OrderProduct orderProduct) {
        em.createQuery("delete from OrderProduct op where op.id=:orderProductId")
                .setParameter("orderProductId", orderProduct.getId())
                .executeUpdate();
    }
}
