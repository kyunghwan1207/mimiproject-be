package com.example.emart.repository;

import com.example.emart.entity.Order;
import com.example.emart.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public List<Order> findAllByUserId(Long userId) {
        return em.createQuery("select o from Order o join fetch o.orderProducts op where o.user.id=:userId", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Order> findAllByUserIdWithMonth(Long userId, int month) {
        LocalDate now1 = LocalDate.now();
        LocalDate now = now1.plusDays(1);
        LocalDate endDate = now.minusMonths(month);
        System.out.println("now = " + now);
        System.out.println("endDate = " + endDate);
        return em.createQuery("select o from Order o where o.user.id=:userId and o.createdAt between :endDate and :now order by o.createdAt DESC", Order.class)
                .setParameter("userId", userId)
                .setParameter("now", now)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public Order findById(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public void delete(Order findOrder) {
        em.createQuery("delete from Order o where o.id=:orderId")
                .setParameter("orderId", findOrder.getId())
                .executeUpdate();
    }
}
