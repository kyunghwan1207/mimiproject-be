package com.example.emart.dto;

import com.example.emart.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartProductResponseDto {
    private Long cartId;
    private Long id; // productId
    private String name;
    private String description;
    private String thumbnail;
    private int price;
    private int qty; // 카트에 담긴 수량
    private int productQty; // 상품 재고
    private String simplePassword; // 간편 결제 번호
    private int epay; // 사용자 결제 가능 금액

    public CartProductResponseDto(Product p, Long cartId, int qty, int productQty, String simplePassword, int epay) {
        this.cartId = cartId;
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.thumbnail = p.getThumbnail();
        this.price = p.getPrice();
        this.qty = qty;
        this.productQty = productQty;
        this.simplePassword = simplePassword;
        this.epay = epay;
    }
}
