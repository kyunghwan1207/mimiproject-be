package com.example.emart.controller;

import com.example.emart.config.auth.PrincipalDetails;
import com.example.emart.consts.SessionConst;
import com.example.emart.dto.UserInfoResponseDto;
import com.example.emart.dto.UserJoinRequestDTO;
import com.example.emart.dto.UserLoginDTO;
import com.example.emart.dto.UserLoginResponseDTO;
import com.example.emart.entity.User;
import com.example.emart.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.http.HttpResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {
  private final UserService userService;

  // 회원가입
  @PostMapping("/join")
  public User addUser(@Valid @RequestBody UserJoinRequestDTO userJoinRequestDTO) {
    System.out.println("joinRequestDTO = " + userJoinRequestDTO);
    return userService.addUser(userJoinRequestDTO);
  }

  @PostMapping("/login")
  public ResponseEntity<UserLoginResponseDTO> login(
          @Valid @RequestBody UserLoginDTO loginDTO,
          @RequestParam(value = "redirectURL", defaultValue = "/") String redirectURL,
          HttpServletRequest request
  ) throws IllegalArgumentException {
    System.out.println("loginDTO = " + loginDTO);
    String finalRedirectURL;
    try {
      User findUser = userService.getUserInfoByEmail(loginDTO.getEmail());
      if (findUser != null && isSamePw(findUser.getPassword(), loginDTO.getPassword())) {
        HttpSession session = request.getSession(true); // 없으면 새로 생성
        session.setAttribute(SessionConst.NAME, findUser.getId());
//        finalRedirectURL = "redirect:" + redirectURL;
        finalRedirectURL = redirectURL;
        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO(finalRedirectURL);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED); // 202
      } else {
        throw new IllegalArgumentException("입력 정보를 다시 확인해주세요.");
      }
    } catch (IllegalArgumentException e) {
      log.info("로그인에 실패했습니다");
//      finalRedirectURL = "redirect:/login";
      finalRedirectURL = "/login";
      UserLoginResponseDTO responseDTO = new UserLoginResponseDTO(finalRedirectURL);
      return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST); // 400
    }
  }
  public boolean isSamePw(String pw1, String pw2) {
    return pw1.equals(pw2);
  }

  // 아이디 번호를 이용한 사용자 정보 조회
  @GetMapping("/{id}")
  public User getUserInfoById(@PathVariable Long id) {
    System.out.println("id = " + id);
    return userService.getUserInfoById(id);
  }
  @GetMapping("/my-info")
  public ResponseEntity<UserInfoResponseDto> userInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    HttpStatus status;
    if (principalDetails == null || principalDetails.getUser() == null) {
      status = HttpStatus.UNAUTHORIZED;
      return new ResponseEntity<>(status);
    } else {
      status = HttpStatus.OK;
      System.out.println("principalDetails.getUser() = " + principalDetails.getUser());
      System.out.println("principalDetails.getUsername() = " + principalDetails.getUsername());
      UserInfoResponseDto responseDto = UserInfoResponseDto.fromEntity(principalDetails.getUser());
      return new ResponseEntity<>(responseDto, status);
    }
  }

//  // 이메일을 이용한 사용자 정보 조회
//  @GetMapping("")
//  public User getUserInfoByEmail(@RequestParam String email) {
//    System.out.println("email = " + email);
//    return userService.getUserInfoByEmail(email);
//  }

  // 회원정보 변경
  @PutMapping("/{id}")
  public User changeUserInfo(@Valid @RequestBody UserJoinRequestDTO userDTO, @PathVariable Long id) {
    return userService.changeUserInfo(userDTO, id);
  }

  @PostMapping("/login-handle")
  public void loginHandle(HttpServletResponse response) throws IOException {
    System.out.println("loginHandle");
    HttpHeaders headers = new HttpHeaders();
    String redirect_uri = "http://localhost:3000/login"; // 로그인 재시도
    response.addHeader("login_result", "fail");
    response.sendRedirect(redirect_uri);
  }
}
