package com.example.emart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddOrderRequestDto {
    private Long cartId;
    private int totalPrice;
    private List<OrderProductRequestDto> orderProductRequestDtos;
}
