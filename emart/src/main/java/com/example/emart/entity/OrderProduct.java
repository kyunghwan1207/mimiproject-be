package com.example.emart.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "order_product")
@Getter @Setter
@NoArgsConstructor
@ToString
public class OrderProduct extends BaseTime {
    @Id @GeneratedValue
    private Long id;
    private int price;
    private int qty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
