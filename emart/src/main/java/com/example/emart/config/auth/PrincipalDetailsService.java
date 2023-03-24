package com.example.emart.config.auth;

import com.example.emart.entity.User;
import com.example.emart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// SecurityConfig에서 loginProcessingUrl("/login") 할 때,
// "/login" 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 함수 자동 실행
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /***
     * Security session에서 Authentication 타입 객체를 관리하는데,
     * Authentication 타입 객체는 UserDetails 타입 객체를 담을 수 있다
     */
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> findUser = userRepository.getUserInfoByEmail(email);
        if (findUser.isPresent()) {
            return new PrincipalDetails(findUser.get());
            // return 한 UserDetails 가 Authentication 객체에 의해 관리되고
            // 그 Authentication 객체는 Security Session에서 관리된다.
        }
        return null;
    }
}
