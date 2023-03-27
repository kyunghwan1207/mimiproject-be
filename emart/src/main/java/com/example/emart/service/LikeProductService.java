package com.example.emart.service;

import com.example.emart.entity.LikeProduct;
import com.example.emart.entity.Product;
import com.example.emart.entity.User;
import com.example.emart.repository.LikeProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeProductService {
    private final LikeProductRepository likeProductRepository;

    @Transactional
    public void addLike(LikeProduct likeProduct, User user, Product product) {
        user.addLikeProduct(likeProduct);
        product.addLikeProduct(likeProduct);
        likeProductRepository.save(likeProduct);
    }

    public List<LikeProduct> findAllWithProduct() {
        return likeProductRepository.findLikeProductWithProduct();
    }

    public List<LikeProduct> findAllWithProduct(Long userId) {
        return likeProductRepository.findLikeProductWithProductByUserId(userId);
    }
}
