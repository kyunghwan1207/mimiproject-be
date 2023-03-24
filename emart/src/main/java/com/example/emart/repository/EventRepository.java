package com.example.emart.repository;

import com.example.emart.entity.Event;
import com.example.emart.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class EventRepository {
  private final EntityManager em;

  public List<Event> getEvents() {
    return em.createQuery("SELECT e FROM Event e", Event.class).getResultList();
  }

  public Event getEvent(Long id) {
    return em.find(Event.class, id);
  }

  public List<Product> getEventProducts(Event event) {
    return em.createQuery("SELECT ep.product FROM EventProduct ep WHERE ep.event=:event", Product.class)
        .setParameter("event", event)
        .getResultList();
  }
}
