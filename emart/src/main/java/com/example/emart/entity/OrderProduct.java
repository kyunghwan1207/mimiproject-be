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
    private int price; // 주문 가격
    private int qty; // 주문 수량

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void addQty(int stockQty) {
        this.qty += stockQty;
    }
    public static OrderProduct createOrderProduct(Product product, int orderPrice, int orderQty) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setPrice(orderPrice);
        orderProduct.setQty(orderQty);
        product.removeStock(orderQty);
        return orderProduct;
    }

    public void cancel() {
        getProduct().addQty(this.qty);
    }
}
