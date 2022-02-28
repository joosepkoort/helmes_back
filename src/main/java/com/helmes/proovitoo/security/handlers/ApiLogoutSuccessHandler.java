package com.helmes.proovitoo.security.handlers;

import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiLogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse response,
                                Authentication authentication) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
