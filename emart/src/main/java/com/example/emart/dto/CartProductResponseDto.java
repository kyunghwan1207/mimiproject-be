package com.example.emart.dto;

import com.example.emart.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartProductResponseDto {
    private Long id; // productId
    private String name;
    private String description;
    private String thumbnail;
    private int price;
    private int qty; // cartCount

    public CartProductResponseDto(Product p, int qty) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.thumbnail = p.getThumbnail();
        this.price = p.getPrice();
        this.qty = qty;
    }
}
