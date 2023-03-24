package com.example.emart.service;

import com.example.emart.entity.Event;
import com.example.emart.entity.Product;
import com.example.emart.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {
  private final EventRepository eventRepository;

  public List<Event> getEventAll() {
    return eventRepository.findAll();
  }

  public List<Product> getEventProducts(Long id) {
    Event event= eventRepository.getEvent(id);
    return eventRepository.getEventProducts(event);
  }
  @Transactional
  public Event addEvent(Event event1) {
    return eventRepository.save(event1);
  }
}
