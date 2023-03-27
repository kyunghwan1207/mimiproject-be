package com.example.emart.controller;

import com.example.emart.config.auth.PrincipalDetails;
import com.example.emart.dto.CartAddRequestDTO;
import com.example.emart.dto.CartProductDto;
import com.example.emart.dto.CartProductResponseDto;
import com.example.emart.entity.Cart;
import com.example.emart.entity.Product;
import com.example.emart.entity.User;
import com.example.emart.service.CartService;
import com.example.emart.service.ProductService;
import com.example.emart.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.emart.config.auth.AuthCheckInterceptor.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
@Slf4j
public class CartsController {
  private final CartService cartService;
  private final UserService userService;
  private final ProductService productService;

  // 특정 사용자 장바구니에서 전체 상품 리스트 조회
  @GetMapping("")
  public ResponseEntity getAllCartProductList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    if (!isLogin(principalDetails)) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    List<CartProductDto> cartProductDtos = cartService.getAllCartProductList(principalDetails.getUser().getId());
    User user = principalDetails.getUser();
    String simplePassword = user.getSimplePassword();
    int epay = user.getEpay();
    List<CartProductResponseDto> responseDtos =
            cartProductDtos.stream().map(cp ->
                    new CartProductResponseDto(cp.getProduct(), cp.getCartId(), cp.getQty(), cp.getProduct().getQty(), simplePassword, epay)).collect(Collectors.toList());
    return new ResponseEntity(responseDtos, HttpStatus.ACCEPTED);
  }

  // 특정 사용자 장바구니에 상품 담기
  @PostMapping("/add")
  public ResponseEntity addCartProduct(@RequestBody CartAddRequestDTO cartAddRequestDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
    if (!isLogin(principalDetails)) {
      log.info("cart fail to addCartProduct() | 401 UNAUTHORIZED");
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
    }
    try {
      Product product = productService.getProductWithInitializedCarts(cartAddRequestDTO.getProductId());
      /**
       * 기존 상품이 이미 장바구니에 있었다면
       * requestDto.getQty()와 기존 장바구니에 담긴 상품의 qty값을 합친게 < product.getQty()
       * 위 조건에 true라면 기존 장바구니에서 qty개수를 update 해줘야한다
       * */
      Optional<Cart> optCart = cartService.findCart(principalDetails.getUser().getId(), product);
      if (optCart.isPresent()) {
        if (canAddToExistCart(optCart.get(), product, cartAddRequestDTO.getQty())) {
          // product.qty 감소는 주문(Order)에서 실행
          Cart cart = optCart.get();
          int newQty = cart.getQty() + cartAddRequestDTO.getQty();
          cartService.updateQty(cart, newQty);
          userService.updateCart(principalDetails.getUser().getId(), cart); // product쪽 cart까지 update처리
          return new ResponseEntity(HttpStatus.NO_CONTENT); // 204
        } else {
          // 수량 부족인 경우
          return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE); // 406
        }
      } else { // 장바구니에 처음 추가한 경우
        // product.qty 감소는 주문(Order)에서 실행
        if (canAddFirstCart(product, cartAddRequestDTO.getQty())) {
          Cart cart = new Cart(product, cartAddRequestDTO.getQty());
          cartService.save(cart);
          userService.addCart(principalDetails.getUser().getId(), cart); // product쪽 cart까지 update처리
          return new ResponseEntity(HttpStatus.CREATED); // 201
        } else {
          // 수량 부족인 경우
          return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE); // 406
        }
      }

    } catch (UsernameNotFoundException e1) {
      return new ResponseEntity(HttpStatus.NOT_FOUND); // 404
    } catch (Exception e2) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
  }

  // 특정 사용자 장바구니에서 수량 업데이트
//  @PutMapping("/update/{id}")
//  public Cart changeCartInfo(@RequestBody CartAddRequestDTO cartDTO, @PathVariable Long id) {
//    return cartService.changeCartQty(cartDTO.getQty(), id);
//  }

  // 특정 사용자 장바구니에서 상품 삭제
  @DeleteMapping("/{id}")
  public void deleteCartProduct(@PathVariable Long id) {
    cartService.deleteCartProduct(id);
  }

  private boolean canAddToExistCart(Cart cart, Product product, int newQty) {
    return (cart.getQty() + newQty <= product.getQty());
  }
  private boolean canAddFirstCart(Product product, int newQty) {
    return (product.getQty() >= newQty);
  }
}
