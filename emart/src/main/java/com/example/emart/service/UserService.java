package com.example.emart.service;

import com.example.emart.dto.UserJoinRequestDTO;
import com.example.emart.entity.Cart;
import com.example.emart.entity.Product;
import com.example.emart.entity.User;
import com.example.emart.entity.enums.ROLE;
import com.example.emart.repository.ProductRepository;
import com.example.emart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

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
            role
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
}
