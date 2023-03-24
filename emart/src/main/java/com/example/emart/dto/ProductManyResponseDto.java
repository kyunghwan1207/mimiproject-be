package com.example.emart.dto;

import com.example.emart.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductManyResponseDto {
    private Long id;
    private String name;
    private String thumbnail;
    private String description;
    private int price;
    private double discount;
    private String brand;
    private double rating;
    private int qty;

    public ProductManyResponseDto(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.thumbnail = p.getThumbnail();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.discount = p.getDiscount();
        this.brand = p.getBrand();
        this.rating = p.getRating();
        this.qty = p.getQty();
    }
}
