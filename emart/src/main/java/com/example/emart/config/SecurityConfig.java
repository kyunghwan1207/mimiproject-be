package com.example.emart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // IoC 빈(Bean)을 등록
@EnableWebSecurity // Spring Security 필터가 Spring 필터체인에 등록됨(필터체인 관리 시작 어노테이션)
//  지금부터 등록할 필터가 기본 필터에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// @Secured 어노테이션 활성화(controller에서 확인가능), @PreAuthorize 과 @PostAuthorize어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    public BCryptPasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/users/**").authenticated()
                .antMatchers("/api/v1/admins/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/api/login")
                .usernameParameter("email") // ** username이 아니라 email으로 param을 받음 **
                .loginProcessingUrl("/api/login") // login주소가 호출되면 Spring Security가 낚아채서 대신 로그인 진행
                .failureForwardUrl("/api/v1/users/login-handle")
                .defaultSuccessUrl("http://localhost:3000");
    }
}
