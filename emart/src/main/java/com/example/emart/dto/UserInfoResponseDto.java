package com.example.emart.dto;

import com.example.emart.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoResponseDto {
    private String email;
    private String username;
    private String address;
    private String phoneNumber;
    private int count; // 장바구니 담은 상품 가짓수
    private int epay;
    public UserInfoResponseDto(User user, int count) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.count = count;
        this.epay = user.getEpay();
    }
    public UserInfoResponseDto(String email, String username, String address, String phoneNumber) {
        this.email = email;
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static UserInfoResponseDto convertUserInfoResponseDtoWithoutCount(User user) {
        return new UserInfoResponseDto(
                user.getEmail(),
                user.getUsername(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }
}
