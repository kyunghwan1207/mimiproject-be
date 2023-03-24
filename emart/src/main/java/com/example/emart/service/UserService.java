package com.example.emart.service;

import com.example.emart.dto.UserJoinRequestDTO;
import com.example.emart.entity.User;
import com.example.emart.entity.enums.ROLE;
import com.example.emart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder encoder;

  @Transactional
  public User addUser(UserJoinRequestDTO userDTO) {
    ROLE role = ROLE.ROLE_USER;
    if (userDTO.getAddress().startsWith("admin")) {
      role = ROLE.ROLE_ADMIN;
    }
    User user = new User(
            userDTO.getName(),
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
      user.setUsername(userDTO.getName());
      user.setEmail(userDTO.getEmail());
      user.setPassword(userDTO.getPassword());
      userRepository.save(user);
    }
    return user;
  }
}
