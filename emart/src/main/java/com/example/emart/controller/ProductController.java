package com.example.emart.controller;

import com.example.emart.config.auth.PrincipalDetails;
import com.example.emart.dto.LikeProductRequestDto;
import com.example.emart.dto.ProductManyResponseDto;
import com.example.emart.dto.ProductResponseDto;
import com.example.emart.entity.LikeProduct;
import com.example.emart.entity.Product;
import com.example.emart.entity.User;
import com.example.emart.service.LikeProductService;
import com.example.emart.service.ProductService;
import com.example.emart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.emart.config.auth.AuthCheckInterceptor.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
  private final ProductService productService;
  private final UserService userService;
  private final LikeProductService likeProductService;

  // 상품 전체 리스트 조회
  @GetMapping("")
  public ResponseEntity getAllProductsList() {
    List<Product> productList = productService.getAllProductsList();
    List<ProductManyResponseDto> productResponseDtoList = productList.stream().map(p -> new ProductManyResponseDto(p)).collect(Collectors.toList());
    return new ResponseEntity(productResponseDtoList, HttpStatus.ACCEPTED); // 202
  }

  // 특정 상품 상세보기
  @GetMapping("/{productId}")
  public ResponseEntity getProductDetail(
          @PathVariable Long productId,
          @AuthenticationPrincipal PrincipalDetails principalDetails) {
    Product product = productService.findOne(productId);
    ProductResponseDto responseDto;
    if (!isLogin(principalDetails)) {
      responseDto = new ProductResponseDto(product, false);
      return new ResponseEntity(responseDto, HttpStatus.OK);

    } else {
      User user = principalDetails.getUser();
      List<LikeProduct> likeProducts = userService.findLikeProducts(user.getId());

      for (LikeProduct likeProduct : likeProducts) {
        if (likeProduct.getProduct().getId() == product.getId()) { // 좋아요한 상품인 경우
          responseDto = new ProductResponseDto(product, true);
          return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
        }
      }
      // 좋아요한 상품이 아닌경우
      responseDto = new ProductResponseDto(product, false);
      return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
    }
  }

  @PostMapping("/like-product")
  public ResponseEntity likeProduct(@RequestBody LikeProductRequestDto requestDto,
                                    @AuthenticationPrincipal PrincipalDetails principalDetails) {
    if (!isLogin(principalDetails)) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    User user = userService.getUserWithInitializedProduct(principalDetails.getUser().getId());
    Boolean newState = requestDto.getState();
    Long productId = requestDto.getProductId();
    if (newState) { // 좋아요 누른 경우
      Product product = productService.findOne(productId);
      LikeProduct likeProduct = new LikeProduct(product);
      likeProductService.addLike(likeProduct, user, product);
    } else { // 좋아요 취소한 경우
      List<LikeProduct> likeProducts = likeProductService.findAllWithProduct();
      int flag = 0;
      for (LikeProduct likeProduct : likeProducts) {
        if (likeProduct.getProduct().getId() == productId) {
          userService.cancelLikeProduct(user.getId(), productId);
          flag = 1;
          break;
        }
      }
      if (flag == 0) { // 좋아요한 상품이 아닌데 좋아요 취소한 경우
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
      }
    }
    return new ResponseEntity(HttpStatus.ACCEPTED);
  }

  @GetMapping("/like-products")
  public ResponseEntity getLikeProductList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    if (!isLogin(principalDetails)) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    User user = principalDetails.getUser();
    List<LikeProduct> likeProducts = likeProductService.findAllWithProduct(user.getId());
    List<Product> products = new ArrayList<>();
    for (LikeProduct likeProduct : likeProducts) {
      products.add(likeProduct.getProduct());
    }
    List<ProductResponseDto> responseDtos = products.stream().map(p -> new ProductResponseDto(p)).collect(Collectors.toList());
    for (ProductResponseDto responseDto : responseDtos) {
      System.out.println("responseDto = " + responseDto);
    }
    return new ResponseEntity(responseDtos, HttpStatus.OK);
//    List<Product> products = productService.findProductInLikeProductByUserId(principalDetails.getUser().getId());
//    for (Product product : products) {
//      System.out.println("product = " + product);
//    }
//    return new ResponseEntity(HttpStatus.OK);
  }

  // 상품명 검색 결과 리스트 조회
  @GetMapping("/search")
  public ResponseEntity getProductSearchList(@RequestParam String name) {
    System.out.println("name = " + name);
    List<Product> products = productService.getProductSearchList(name);
    List<ProductManyResponseDto> responseDtos = products.stream().map(p -> new ProductManyResponseDto(p)).collect(Collectors.toList());
    return new ResponseEntity(responseDtos, HttpStatus.ACCEPTED);
  }

}