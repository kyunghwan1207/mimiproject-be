package com.example.emart.service;

import com.example.emart.entity.OrderProduct;
import com.example.emart.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public List<OrderProduct> findAllWithProduct(Long orderId) {
        return orderProductRepository.findAllWithProduct(orderId);
    }

    @Transactional
    public void delete(OrderProduct orderProduct) {
        orderProductRepository.delete(orderProduct);
    }
}
