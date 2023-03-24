package com.example.emart.controller;

import com.example.emart.dto.AdResponseDto;
import com.example.emart.entity.Advertisement;
import com.example.emart.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @GetMapping("")
    public ResponseEntity getAdsList() {
        List<Advertisement> adList = advertisementService.findAll();
        for (Advertisement ad : adList) {
            System.out.println("AdController / ad = " + ad);
        }
        System.out.println("adList.size() = " + adList.size());
        List<AdResponseDto> adResponseDtoList =
                adList.stream().map(a -> new AdResponseDto(a)).collect(Collectors.toList());
        for (AdResponseDto adResponseDto : adResponseDtoList) {
            System.out.println("adResponseDto = " + adResponseDto);
        }
        return new ResponseEntity(adResponseDtoList, HttpStatus.ACCEPTED);
    }
}
