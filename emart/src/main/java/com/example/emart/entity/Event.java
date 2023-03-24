package com.example.emart.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "event")
@Setter @Getter
@NoArgsConstructor
public class Event extends BaseTime {
  @Id
  @GeneratedValue
  private Long id;

  private String name;
}
