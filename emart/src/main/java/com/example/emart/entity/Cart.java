package com.example.emart.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "carts")
@Setter @Getter
@NoArgsConstructor
public class Cart extends BaseTime {
  @Id
  @GeneratedValue
  private Long id;

  private int qty;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id")
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="product_id")
  private Product product;


}
