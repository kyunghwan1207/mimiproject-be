package com.example.emart.controller;

import com.example.emart.config.auth.PrincipalDetails;
import com.example.emart.dto.AddOrderRequestDto;
import com.example.emart.dto.OrderProductManyResponseDto;
import com.example.emart.entity.*;
import com.example.emart.entity.enums.ORDER_STATUS;
import com.example.emart.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity addOrder(
            @RequestBody AddOrderRequestDto requestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        // 주문 추가
        orderService.addOrder(principalDetails, requestDto);
        // 장바구니에서 삭제하는 로직
        cartService.deleteProductByUserId(user.getId(), requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity getOrders(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        User user = userService.getUserInfoById(principalDetails.getUser().getId());
        List<Order> orders = orderService.findAllByUserId(user.getId());
        List<OrderProductManyResponseDto> responseDtos = new ArrayList<>();
        for (Order order : orders) {
            List<OrderProduct> orderProducts = orderProductService.findAllWithProduct(order.getId()); //order.getOrderProducts();
            List<Product> products = new ArrayList<>();
            for (OrderProduct orderProduct : orderProducts) {
                products.add(orderProduct.getProduct());
            }
            responseDtos.add(new OrderProductManyResponseDto(order, orderProducts, products));
        }
        if (responseDtos.size() == 0) { // 주문 내역이 없는 경우
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(responseDtos, HttpStatus.OK);
    }
    @GetMapping("/{month}")
    public ResponseEntity getOrderWithMonthFiltering(
            @PathVariable int month,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        List<Order> orders = orderService.findAllWithMonth(principalDetails.getUser().getId(), month);
        List<OrderProductManyResponseDto> responseDtos = new ArrayList<>();
        for (Order order : orders) {
            List<OrderProduct> orderProducts = orderProductService.findAllWithProduct(order.getId()); //order.getOrderProducts();
            List<Product> products = new ArrayList<>();
            for (OrderProduct orderProduct : orderProducts) {
                products.add(orderProduct.getProduct());
            }
            responseDtos.add(new OrderProductManyResponseDto(order, orderProducts, products));
        }
        if (responseDtos.size() == 0) { // 주문 내역이 없는 경우
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(responseDtos, HttpStatus.OK);
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity deleteOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        List<OrderProduct> orderProducts = orderProductService.findAllWithProduct(orderId);
        try {
            for (OrderProduct orderProduct : orderProducts) {
                productService.cancelOrder(principalDetails.getUser().getId(), orderId ,orderProduct);
            }
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }
}
