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

    public static UserInfoResponseDto fromEntity(User user) {
        return new UserInfoResponseDto(
                user.getEmail(),
                user.getUsername(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }
}
