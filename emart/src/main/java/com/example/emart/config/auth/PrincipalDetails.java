package com.example.emart.config.auth;

import com.example.emart.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;
    @Autowired
    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return user.getUsername();
    }

    public User getUser() {
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return user;
    }
    public void setEmail(String email) {
        user.setEmail(email);
    }
    public void setUserName(String username) {
        user.setUsername(username);
    }
    public void setPassword(String password) {
        user.setPassword(password);
    }

    public void setPhoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 1년동안 회원이 로그인을 안하면 휴먼계정으로 하기로 한다면?
        // model/User에서  loginDate(로그인한 시점 기록)라는 컬럼이 필요하다
        // logtime = user.getLogindDte
        // now_teim - logtime 이 1년을 초과하면 return false;
        return true;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
