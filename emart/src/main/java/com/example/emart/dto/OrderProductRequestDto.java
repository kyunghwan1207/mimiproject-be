package com.example.emart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class OrderProductRequestDto {
    private Long productId; // 상품 id
    private int price;
    private int orderQty; // 주문 수량
    private int productQty; // 상품 재고
}
