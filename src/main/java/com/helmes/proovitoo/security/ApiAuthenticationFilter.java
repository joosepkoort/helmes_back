package com.helmes.proovitoo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public ApiAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

    LoginCredentials loginCredentials;
        try{
             loginCredentials =  new ObjectMapper().readValue(request.getInputStream(),LoginCredentials.class);
        } catch (Exception e)
        {
            throw new BadCredentialsException("");
        }

        logger.info("Login credentials: "+loginCredentials);

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(
                    loginCredentials.getUserName(),
                        loginCredentials.getPassword());
        logger.info("Auth request: "+authRequest);

        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin",  origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
