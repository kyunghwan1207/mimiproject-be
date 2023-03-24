package com.example.emart.controller;

import com.example.emart.dto.CartAddRequestDTO;
import com.example.emart.entity.Cart;
import com.example.emart.entity.Product;
import com.example.emart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartsController {
  private final CartService cartService;

  // 특정 사용자 장바구니에서 전체 상품 리스트 조회
  @GetMapping("")
  public List<Product> getAllCartProductList(@RequestParam Long userId) {
    return cartService.getAllCartProductList(userId);
  }

  // 특정 사용자 장바구니에 상품 담기
  @PostMapping("/add")
  public Cart addCartProduct(@RequestBody CartAddRequestDTO cartAddRequestDTO) {
    return cartService.addProduct(cartAddRequestDTO);
  }

  // 특정 사용자 장바구니에서 수량 업데이트
  @PutMapping("/update/{id}")
  public Cart changeCartInfo(@RequestBody CartAddRequestDTO cartDTO, @PathVariable Long id) {
    return cartService.changeCartQty(cartDTO.getQty(), id);
  }

  // 특정 사용자 장바구니에서 상품 삭제
  @DeleteMapping("/delete/{id}")
  public void deleteCartProduct(@PathVariable Long id) {
    cartService.deleteCartProduct(id);
  }
}
