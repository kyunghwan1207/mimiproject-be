package com.example.emart.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
@Setter @Getter
@NoArgsConstructor
public class Event extends BaseTime {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private List<EventProduct> eventProducts = new ArrayList<>();

  public Event(String name) {
    this.name = name;
  }

}
