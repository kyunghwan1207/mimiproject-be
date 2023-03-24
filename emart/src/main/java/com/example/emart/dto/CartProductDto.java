package com.example.emart.dto;

import com.example.emart.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDto {
    private Product product;
    private int qty;
}
