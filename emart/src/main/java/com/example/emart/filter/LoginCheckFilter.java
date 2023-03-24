package com.example.emart.filter;

import com.example.emart.consts.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LoginCheckFilter implements Filter {
    private static final String[] WHITE_LIST = {
            "/", "/api/v1/users/login", "/api/v1/users/join", "/api/v1/users/logout", "/css/*"
    };
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("login filter init");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURI = httpServletRequest.getRequestURI(); // 유일한 식별자
        String uuid = UUID.randomUUID().toString();
        System.out.println("LoginCheckFilter.doFilter");
        System.out.println("requestURI = " + requestURI);
        try {
            log.info("로그인 인증 필터 시작 / requestURI : [{}], uuid: [{}]", requestURI, uuid);
            if (isLoginCheckPath(requestURI)) {
                HttpSession requestSession = httpServletRequest.getSession(false); // 없으면 null
                if (requestSession == null || requestSession.getAttribute(SessionConst.NAME) == null) {
                    log.info("미 인증 사용자의 요청 requestURI : {}", requestURI);
                    httpServletResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return; // 미 인증 사용자는 다음 필터로 forward 하지 않음
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("로그인 인증 필터 종료 / requestURI : [{}], uuid : [{}]", requestURI, uuid);
        }
    }

    @Override
    public void destroy() {
        log.info("login filter destroy");
    }

    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
