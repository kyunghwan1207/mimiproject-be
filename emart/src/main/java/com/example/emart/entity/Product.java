package com.example.emart.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Setter
@Getter
@NoArgsConstructor
public class Product extends BaseTime{
  @Id
  @GeneratedValue
  private Long id;
  private int price;
  private int qty;
  private double discount;
  private double rating;
  private String name;
  private String thumbnail;
  private String description;
  private String brand;

  @OneToMany(mappedBy = "product")
  private List<EventProduct> eventProducts = new ArrayList<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<LikeProduct> likeProducts = new ArrayList<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<Board> boards = new ArrayList<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<OrderProduct> orderProducts = new ArrayList<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<ProductComment> productComments = new ArrayList<>();

}
