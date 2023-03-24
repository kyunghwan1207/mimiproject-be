package com.example.emart.service;

import com.example.emart.entity.Advertisement;
import com.example.emart.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    @Transactional
    public Advertisement addAd(Advertisement ad) {
        return advertisementRepository.save(ad);
    }

    public List<Advertisement> findAll() {
        return advertisementRepository.findAll();
    }
}
