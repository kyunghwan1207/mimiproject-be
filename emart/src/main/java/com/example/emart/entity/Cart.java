package com.example.emart.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "carts")
@Setter @Getter
@NoArgsConstructor
@ToString
public class Cart extends BaseTime {
  @Id
  @GeneratedValue
  private Long id;

  private int qty;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="product_id")
  private Product product;

  public Cart(Product product, int qty) {
    this.product = product;
    this.qty = qty;
  }

}
