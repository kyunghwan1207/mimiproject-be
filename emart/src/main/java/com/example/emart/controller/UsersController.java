package com.example.emart.controller;

import com.example.emart.config.auth.PrincipalDetails;
import com.example.emart.dto.*;
import com.example.emart.entity.User;
import com.example.emart.service.CartService;
import com.example.emart.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static com.example.emart.config.auth.AuthCheckInterceptor.isLogin;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {
  private final UserService userService;
  private final CartService cartService;

  // 회원가입
  @PostMapping("/join")
  public User addUser(@Valid @RequestBody UserJoinRequestDTO userJoinRequestDTO) {
    System.out.println("joinRequestDTO = " + userJoinRequestDTO);
    return userService.addUser(userJoinRequestDTO);
  }

  // 아이디 번호를 이용한 사용자 정보 조회
  @GetMapping("/{id}")
  public User getUserInfoById(@PathVariable Long id) {
    System.out.println("id = " + id);
    return userService.getUserInfoById(id);
  }
  /**
   * 사용자 정보 + 장바구니 담은 상품 개수 함께 return
   * */
  @GetMapping("/my-info")
  public ResponseEntity<UserInfoResponseDto> userInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    HttpStatus status;
    if (!isLogin(principalDetails)) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } else {
      status = HttpStatus.ACCEPTED;
      User user = userService.getUserInfoById(principalDetails.getUser().getId());
      int cartCount = cartService.findCartCountWithUserId(user.getId());
      System.out.println("cartCount = " + cartCount);
      UserInfoResponseDto responseDto = new UserInfoResponseDto(user, cartCount);
      return new ResponseEntity<>(responseDto, status);
    }
  }
  @GetMapping("/check-login-state")
  public ResponseEntity checkLoginState(@AuthenticationPrincipal PrincipalDetails principalDetails){
    HttpStatus status = HttpStatus.OK;
    if (!isLogin(principalDetails)) {
      status = HttpStatus.UNAUTHORIZED;
    }
    return new ResponseEntity(status);
  }
  /**
   * 이메일 중복 검사
   * */
  @PostMapping("/check-email")
  public ResponseEntity checkEmail(
          @Valid @RequestBody UserCheckEmailRequestDto requestDto) {
    System.out.println("requestDto = " + requestDto);
    User finUser = userService.getUserInfoByEmail(requestDto.getEmail());
    System.out.println("finUser = " + finUser);
    HttpStatus status;
    if (finUser == null) {
      status = HttpStatus.OK;
    } else {
      status = HttpStatus.CONFLICT; // 409
    }
    return new ResponseEntity(status);
  }

  @PostMapping("/check-password")
  public ResponseEntity checkPassword(
          @RequestBody CheckPasswordRequestDto requestDto,
          @AuthenticationPrincipal PrincipalDetails principalDetails) {
    if (!isLogin(principalDetails)) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    User user = userService.getUserInfoById(principalDetails.getUser().getId());
    boolean isSame = userService.isSamePw(user.getPassword(), requestDto.getPassword());

    HttpStatus status = HttpStatus.OK;
    if (!isSame) {
      status = HttpStatus.BAD_REQUEST;
    }
    return new ResponseEntity(status);
  }

  // 회원정보 변경
  @PutMapping("/edit-email")
  public ResponseEntity<UserInfoResponseDto> editEmail(
          @Valid @RequestBody EditEmailRequestDto requestDto,
          @AuthenticationPrincipal PrincipalDetails principalDetails) {
    if (!isLogin(principalDetails)) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    try {
      User user = userService.changeEmail(requestDto.getNewEmail(), principalDetails.getUser().getId(), principalDetails);
      UserInfoResponseDto responseDto = UserInfoResponseDto.convertUserInfoResponseDtoWithoutCount(user);

      return new ResponseEntity(responseDto, HttpStatus.OK);
    } catch (Exception e) {
      log.info("Exception= ", e);
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/edit-username")
  public ResponseEntity<UserInfoResponseDto> editUserName(
          @Valid @RequestBody EditUserNameRequestDto requestDto,
          @AuthenticationPrincipal PrincipalDetails principalDetails) {
    if (!isLogin(principalDetails)) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    try {
      User user = userService.changeUserName(requestDto.getNewUserName(), principalDetails.getUser().getId(), principalDetails);
      UserInfoResponseDto responseDto = UserInfoResponseDto.convertUserInfoResponseDtoWithoutCount(user);
      return new ResponseEntity(responseDto, HttpStatus.OK);
    } catch (Exception e) {
      log.info("Exception= ", e);
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/edit-phoneNumber")
  public ResponseEntity<UserInfoResponseDto> editPhoneNumber(
          @Valid @RequestBody EditPhoneNumberRequestDto requestDto,
          @AuthenticationPrincipal PrincipalDetails principalDetails) {
    if (!isLogin(principalDetails)) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    try {
      User user = userService.changePhoneNumber(requestDto.getNewPhoneNumber(), principalDetails.getUser().getId(), principalDetails);
      UserInfoResponseDto responseDto = UserInfoResponseDto.convertUserInfoResponseDtoWithoutCount(user);
      return new ResponseEntity(responseDto, HttpStatus.OK);
    } catch (Exception e) {
      log.info("Exception= ", e);
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
  }
  @PutMapping("/charge-epay")
  public ResponseEntity chargeEpay(@RequestBody ChargeEpayRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
    ChargeEpayResponseDto responseDto = userService.chargeEpay(principalDetails, requestDto.getEpay());
    return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
  }

  @PutMapping("/edit-password")
  public ResponseEntity<UserInfoResponseDto> editPassword(
          @Valid @RequestBody EditPasswordRequestDto requestDto,
          @AuthenticationPrincipal PrincipalDetails principalDetails) {
    if (!isLogin(principalDetails)) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    try {
      User user = userService.changePassword(requestDto.getPassword(), principalDetails.getUser().getId(), principalDetails);
      UserInfoResponseDto responseDto = UserInfoResponseDto.convertUserInfoResponseDtoWithoutCount(user);

      return new ResponseEntity(responseDto, HttpStatus.OK);
    } catch (Exception e) {
      log.info("Exception= ", e);
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/edit-address")
  public ResponseEntity<UserInfoResponseDto> editAddress(
          @Valid @RequestBody EditAddressRequestDto requestDto,
          @AuthenticationPrincipal PrincipalDetails principalDetails) {
    if (!isLogin(principalDetails)) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    try {
      User user = userService.changeAddress(requestDto.getNewAddress(), principalDetails.getUser().getId(), principalDetails);
      UserInfoResponseDto responseDto = UserInfoResponseDto.convertUserInfoResponseDtoWithoutCount(user);

      return new ResponseEntity(responseDto, HttpStatus.OK);
    } catch (Exception e) {
      log.info("Exception= ", e);
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * 로그인 실패시 로직처리하는 함수
   * */
  @PostMapping("/login-handle") 
  public void loginHandle(HttpServletResponse response) throws IOException {
    System.out.println("loginHandle");
    HttpHeaders headers = new HttpHeaders();
    String redirect_uri = "http://localhost:3000/login-retry"; // 로그인 재시도
    response.addHeader("login_result", "fail");
    response.sendRedirect(redirect_uri);
  }

  @GetMapping("/logout")
  public ResponseEntity logout(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    if (!isLogin(principalDetails)) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    principalDetails.setUser(null);
    return new ResponseEntity(HttpStatus.OK);
  }
}
