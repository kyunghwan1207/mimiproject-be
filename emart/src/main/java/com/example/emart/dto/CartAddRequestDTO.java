package com.example.emart.dto;

import lombok.Data;

@Data
public class CartAddRequestDTO {
  private Long productId;
  private int qty;
}