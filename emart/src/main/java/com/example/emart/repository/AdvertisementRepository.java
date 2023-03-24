package com.example.emart.repository;

import com.example.emart.entity.Advertisement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdvertisementRepository {
    private final EntityManager em;

    public Advertisement save(Advertisement ad) {
        em.persist(ad);
        return ad;
    }

    public List<Advertisement> findAll() {
        List<Advertisement> adList = em.createQuery("select a from Advertisement a", Advertisement.class).getResultList();
        for (Advertisement ad : adList) {
            System.out.println("ad = " + ad);
        }
        return adList;
    }
}
