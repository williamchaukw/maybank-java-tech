package com.kwchau.MaybankBackendTechTest.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!isAuthenticated(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
            return false;
        }
        return true;
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        return "token".equalsIgnoreCase(authToken);
    }
}
