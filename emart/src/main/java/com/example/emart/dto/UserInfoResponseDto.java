package com.example.emart.dto;

import com.example.emart.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {
    private String email;
    private String username;
    private String address;
    private String phoneNumber;
    private int count; // 장바구니 담은 상품 가짓수
    public UserInfoResponseDto(User user, int count) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.count = count;
    }
}
