package com.example.emart.dto;

import lombok.Data;

@Data
public class ChargeEpayResponseDto {
    private int epay;

    public ChargeEpayResponseDto(int epay) {
        this.epay = epay;
    }
}
