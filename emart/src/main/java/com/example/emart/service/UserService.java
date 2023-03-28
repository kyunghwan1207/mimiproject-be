package com.example.emart.service;

import com.example.emart.config.auth.PrincipalDetails;
import com.example.emart.dto.ChargeEpayResponseDto;
import com.example.emart.dto.UserJoinRequestDTO;
import com.example.emart.entity.Cart;
import com.example.emart.entity.LikeProduct;
import com.example.emart.entity.User;
import com.example.emart.entity.enums.ROLE;
import com.example.emart.repository.LikeProductRepository;
import com.example.emart.repository.ProductRepository;
import com.example.emart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final LikeProductService likeProductService;
  private final LikeProductRepository likeProductRepository;


  private final BCryptPasswordEncoder encoder;

  @Transactional
  public User addUser(UserJoinRequestDTO userDTO) {
    ROLE role = ROLE.ROLE_USER;
    if (userDTO.getAddress().startsWith("admin")) {
      role = ROLE.ROLE_ADMIN;
    }
    User user = new User(
            userDTO.getUsername(),
            userDTO.getEmail(),
            encoder.encode(userDTO.getPassword()),
            userDTO.getAddress(),
            userDTO.getPhoneNumber(),
            role,
            userDTO.getSimplePassword()
    );
    userRepository.save(user);
    return user;
  }

  public User getUserInfoById(Long id) {
    return userRepository.getUserInfoById(id).orElse(null);
  }

  public User getUserInfoByEmail(String email) {
    return userRepository.getUserInfoByEmail(email).orElse(null);
  }

  @Transactional
  public User changeUserInfo(UserJoinRequestDTO userDTO, Long id) {
    User user = userRepository.getUserInfoById(id).orElse(null);
    if(user != null) {
      user.setUsername(userDTO.getUsername());
      user.setEmail(userDTO.getEmail());
      user.setPassword(userDTO.getPassword());
      userRepository.save(user);
    }
    return user;
  }

  @Transactional
  public void addCart(Long userId, Cart cart) throws UsernameNotFoundException {
    Optional<User> findUser = userRepository.getUserInfoById(userId);
    if (findUser.isPresent()) {
      User user = findUser.get();
      user.addCart(cart);
      cart.getProduct().addCart(cart);
    } else {
      throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
    }
  }

  @Transactional
  public void updateCart(Long userId, Cart cart) {
    User user = userRepository.getUserInfoById(userId).get();
    user.getCarts().stream()
            .forEach(c -> {
              if (c.getId() == cart.getId()) {
                c.setQty(cart.getQty());
              }
            });
    cart.getProduct().getCarts().stream()
            .forEach(c -> {
              if (c.getId() == cart.getId()) {
                c.setQty(cart.getQty());
              }
            });

  }

  public boolean isSamePw(String userPassword, String checkPassword) {
    return encoder.matches(checkPassword, userPassword);
  }

  @Transactional
  public User changeEmail(String newEmail, Long id, PrincipalDetails principalDetails) {
    User user = userRepository.getUserInfoById(id).orElse(null);
    if(user != null) {
      user.setEmail(newEmail);
      principalDetails.getUser().setEmail(user.getEmail());
    } else {
      throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
    }
    return user;
  }

  @Transactional
  public User changeUserName(String username, Long id, PrincipalDetails principalDetails) {
    User user = userRepository.getUserInfoById(id).orElse(null);
    if(user != null) {
      user.setUsername(username);
      principalDetails.getUser().setAddress(user.getAddress());
    } else {
      throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
    }
    return user;
  }
  @Transactional
  public User changePhoneNumber(String newPhoneNumber, Long id, PrincipalDetails principalDetails) {
    User user = userRepository.getUserInfoById(id).orElse(null);
    if(user != null) {
      user.setUsername(newPhoneNumber);
      principalDetails.getUser().setPhoneNumber(user.getPhoneNumber());
    } else {
      throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
    }
    return user;
  }

  @Transactional
  public User changeAddress(String newAddress, Long id, PrincipalDetails principalDetails) {
    User user = userRepository.getUserInfoById(id).orElse(null);
    if(user != null) {
      user.setUsername(newAddress);
      principalDetails.getUser().setPhoneNumber(user.getAddress());
    } else {
      throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
    }
    return user;
  }
  @Transactional
  public User changePassword(String newPassword, Long id, PrincipalDetails principalDetails) {
    User user = userRepository.getUserInfoById(id).orElse(null);
    if(user != null) {
      user.setPassword(encoder.encode(newPassword));
      principalDetails.getUser().setPassword(user.getPassword());
    } else {
      throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
    }
    return user;

  }

  public User getUserWithInitializedProduct(Long id) {
    User user = userRepository.getUserInfoById(id).orElseThrow(IllegalArgumentException::new);
    user.getLikeProducts().stream().forEach(lp -> lp.getId());
    return user;
  }

  @Transactional
  public void cancelLikeProduct(Long userId, Long productId) {
    User findUser = userRepository.getUserInfoById(userId).orElseThrow(IllegalArgumentException::new);
    List<LikeProduct> likeProducts = likeProductService.findAllWithProduct(userId);
    for (LikeProduct likeProduct : likeProducts) {
      if (likeProduct.getProduct().getId() == productId) {
        findUser.getLikeProducts().remove(likeProduct);
        likeProduct.getProduct().getLikeProducts().remove(likeProduct);
        likeProductRepository.delete(likeProduct);
        break;
      }
    }
  }

  public List<LikeProduct> findLikeProducts(Long userId) {
    User findUser = userRepository.getUserInfoById(userId).get();
    return findUser.getLikeProducts();
  }
  @Transactional
  public ChargeEpayResponseDto chargeEpay(PrincipalDetails principalDetails, int addEpay) {
    User user = principalDetails.getUser();
    int finalEpay = addEpay + user.getEpay();
    userRepository.updateEpayById(user.getId(), finalEpay);
    principalDetails.getUser().setEpay(finalEpay);
    return new ChargeEpayResponseDto(finalEpay);
  }
}
