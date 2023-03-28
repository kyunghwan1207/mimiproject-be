package com.example.emart.service;

import com.example.emart.config.auth.PrincipalDetails;
import com.example.emart.dto.AddOrderRequestDto;
import com.example.emart.dto.OrderProductRequestDto;
import com.example.emart.entity.Order;
import com.example.emart.entity.OrderProduct;
import com.example.emart.entity.Product;
import com.example.emart.entity.User;
import com.example.emart.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;

    @Transactional
    public void addOrder(PrincipalDetails principalDetails, AddOrderRequestDto requestDto) {
        List<OrderProductRequestDto> orderProducts = requestDto.getOrderProductRequestDtos();
        Long userId = principalDetails.getUser().getId();
        User user = userService.getUserInfoById(userId);
        String orderNumber = generateOrderNumber();
        List<OrderProduct> newOrderProductList = new ArrayList<>();
        for (OrderProductRequestDto orderProduct : orderProducts) {
            Product product = productService.getProductInfoById(orderProduct.getProductId());
            OrderProduct newOrderProduct = OrderProduct.createOrderProduct(
                    product, orderProduct.getPrice()* orderProduct.getOrderQty(), orderProduct.getOrderQty()
            );
            newOrderProductList.add(newOrderProduct);
        }
        Order order = Order.createOrder(
                user, orderNumber, newOrderProductList
        );
        orderRepository.save(order);
        int finalEpay= user.getEpay() - requestDto.getTotalPrice();
        principalDetails.getUser().setEpay(finalEpay);
        user.setEpay(finalEpay);
    }

    private String generateOrderNumber() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        return now.format(formatter);
    }

    public List<Order> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public List<Order> findAllWithMonth(Long userId, int month) {
        return orderRepository.findAllByUserIdWithMonth(userId, month);
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public void delete(Order findOrder) {
        orderRepository.delete(findOrder);
    }
}
