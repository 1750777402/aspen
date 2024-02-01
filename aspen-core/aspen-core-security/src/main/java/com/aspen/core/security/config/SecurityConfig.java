package com.aspen.core.security.config;

import com.aspen.core.security.constants.ConstantUtil;
import com.aspen.core.security.filter.CaptchaFilter;
import com.aspen.core.security.filter.JwtAuthenticationFilter;
import com.aspen.core.security.handler.*;
import com.aspen.core.security.service.UserDetailService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Resource
    private CaptchaFilter captchaFilter;

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] URL_WHITELIST = {ConstantUtil.LOGIN_URL, ConstantUtil.CAPTCHA_URL};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用csrf
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用formLogin
                .formLogin(AbstractHttpConfigurer::disable).logout(AbstractHttpConfigurer::disable)
                // 禁用session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 配置拦截规则
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // 白名单
                        .requestMatchers(URL_WHITELIST).permitAll()
                        // 除了白名单以外的全部都需要校验
                        .anyRequest().authenticated())
                // 异常处理器
                .exceptionHandling(exce -> exce.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler))
                // 验证码过滤器放在UsernamePassword过滤器之前
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置自定义的过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
