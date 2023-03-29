package com.example.emart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartAddRequestDTO {
  private Long productId;
  private int qty;
}