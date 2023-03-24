package com.example.emart.dto;

import com.example.emart.entity.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdResponseDto {
    private Long id;
    private String name;
    private String img;
    private String link;

    public AdResponseDto(Advertisement ad) {
        this.id = ad.getId();
        this.name = ad.getName();
        this.img = ad.getImg();
        this.link = ad.getLink();
    }
}
