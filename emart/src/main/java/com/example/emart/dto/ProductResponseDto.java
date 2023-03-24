package com.example.emart.dto;

import com.example.emart.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private String thumbnail;
    private int price;
    private double discount;
    private String brand;
    private double rating;

    public ProductResponseDto(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.thumbnail = p.getThumbnail();
        this.price = p.getPrice();
        this.discount = p.getDiscount();
        this.brand = p.getBrand();
        this.rating = p.getRating();
    }
}
