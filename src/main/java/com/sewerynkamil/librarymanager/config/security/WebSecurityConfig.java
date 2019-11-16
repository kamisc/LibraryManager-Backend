package com.sewerynkamil.librarymanager.config.security;

import com.sewerynkamil.librarymanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Author Kamil Seweryn
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthenticationEntryPointJwt authenticationEntryPoint;
    private UserService userService;
    private RequestFilterJwt requestFilterJwt;

    @Autowired
    public WebSecurityConfig(
            AuthenticationEntryPointJwt authenticationEntryPoint,
            UserService userService,
            RequestFilterJwt requestFilterJwt) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userService = userService;
        this.requestFilterJwt = requestFilterJwt;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers(
                            "/v1/login",
                            "/v1/register",
                            "/v1/users/exist/{email}",
                            "/swagger-ui.html",
                            "/v2/api-docs",
                            "/webjars/**",
                            "/swagger-resources/**",
                            "/configuration/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .addFilterBefore(requestFilterJwt, UsernamePasswordAuthenticationFilter.class);
    }
}