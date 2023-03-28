package com.example.emart.dto;

import com.example.emart.entity.OrderProduct;
import com.example.emart.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
public class OrderProductResponseDto {
    private Long id; // from orderProduct.id
    private int orderQty; // from orderProduct.qty
    private int orderPrice; // from orderProduct.price

    private String name; // productName from product

    private String thumbnail; // from product

    private int price; // from product.price

    public OrderProductResponseDto(OrderProduct orderProduct, Product product) {
        this.id = orderProduct.getId();
        this.orderQty = orderProduct.getQty();
        this.orderPrice = orderProduct.getPrice();
        this.name = product.getName();
        this.thumbnail = product.getThumbnail();
        this.price = product.getPrice();
    }

}
