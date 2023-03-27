package com.example.emart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LikeProductRequestDto {
    private Long productId;
    private Boolean state;
}
