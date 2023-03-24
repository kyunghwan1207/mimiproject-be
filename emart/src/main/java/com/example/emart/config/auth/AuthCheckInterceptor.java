package com.example.emart.config.auth;

public class AuthCheckInterceptor {
    public static boolean isLogin(PrincipalDetails principalDetails) {
        return (principalDetails != null && principalDetails.getUser() != null);
    }
}
