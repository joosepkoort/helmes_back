package com.helmes.proovitoo.conf;

import com.helmes.proovitoo.security.ApiAuthenticationFilter;
import com.helmes.proovitoo.security.handlers.ApiAuthSuccessHandler;
import com.helmes.proovitoo.security.handlers.ApiLogoutSuccessHandler;
import com.helmes.proovitoo.security.handlers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] OPENAPI_UI = {
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().
                csrf().disable();

        http.cors().and()
                .authorizeRequests()
                .antMatchers(OPENAPI_UI).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/logout").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login","/api/register*").permitAll()
                .antMatchers("/api/data")
                .hasAnyRole("ADMIN", "USER").anyRequest()
                .access("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling().authenticationEntryPoint(new ApiEntryPoint());
        http.exceptionHandling().accessDeniedHandler(new ApiAccessDeniedHandler());

        http.logout()
                .logoutUrl("/api/logout")
                .clearAuthentication(true)
                .logoutSuccessHandler(new ApiLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
        http.addFilterAfter(apiLoginFilter("/api/login"), LogoutFilter.class);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("localhost:4200", "http://localhost:4200", "http://turing.cs.ttu.ee", "turing.cs.ttu.ee"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Access-Control-Allow-Headers", "Access-Control-Allow-Methods", "Access-Control-Request-Headers", "Access-Control-Request-Method", "Age", "Allow", "Access-Control-Allow-Origin", "Access-Control-Max-Age", "Accept", "Origin", "Content-Type", "X-Requested-With", "Access-Control-Expose-Headers", "responseType"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "responseType"));
        configuration.setMaxAge(Long.parseLong("3600"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {

        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder());

    }

    public Filter apiLoginFilter(String url) throws Exception {
        ApiAuthenticationFilter filter = new ApiAuthenticationFilter(url);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new ApiAuthSuccessHandler());
        filter.setAuthenticationFailureHandler(new ApiAuthFailureHandler());

        return filter;
    }
}