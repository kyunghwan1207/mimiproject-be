package com.example.emart.dto;

import com.example.emart.entity.Order;
import com.example.emart.entity.OrderProduct;
import com.example.emart.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderProductManyResponseDto {
    private Long orderId;
    private String orderNumber; // orderNumber from orders
    private String orderDate; // from orders

    private List<OrderProductResponseDto> orderProductList = new ArrayList<>();

//    private int totalPrice; // calc
    public OrderProductManyResponseDto(Order order, List<OrderProduct> orderProducts, List<Product> products) {
        this.orderId = order.getId();
        this.orderNumber = order.getNumber();
        this.orderDate = generateOrderDate(order.getCreatedAt());
        int size = orderProducts.size();
        for (int i = 0; i < size; i++) {
            orderProductList.add(new OrderProductResponseDto(orderProducts.get(i), products.get(i)));
        }
    }

    private String generateOrderDate(LocalDate orderDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return orderDate.format(formatter);
    }
}
