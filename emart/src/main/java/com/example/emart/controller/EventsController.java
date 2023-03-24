package com.example.emart.controller;

import com.example.emart.entity.Event;
import com.example.emart.entity.Product;
import com.example.emart.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventsController {

  private final EventService eventService;

  // 전체 이벤트 리스트 조회
  @GetMapping("")
  public List<Event> getEvents() {
    return eventService.getEvents();
  }

  // 특정 이벤트 진행 상품 리스트 조회
  @GetMapping("/{id}")
  public List<Product> getEventProducts(@PathVariable Long id) {
    return eventService.getEventProducts(id);
  }
}
