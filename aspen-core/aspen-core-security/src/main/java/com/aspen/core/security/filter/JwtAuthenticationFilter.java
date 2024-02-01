package com.aspen.core.security.filter;

import com.aspen.core.foundation.base.UserDetailsDTO;
import com.aspen.core.foundation.context.AspenContext;
import com.aspen.core.foundation.context.AspenContextHolder;
import com.aspen.core.security.constants.ConstantUtil;
import com.aspen.core.security.exception.JwtException;
import com.aspen.core.security.jwt.JwtUtils;
import com.aspen.core.security.model.UserDetailsModel;
import com.aspen.core.security.service.UserDetailService;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailService userDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = request.getHeader(ConstantUtil.HEADER);
        // 这里如果没有jwt，继续往后走，因为后面还有鉴权管理器等去判断是否拥有身份凭证，所以是可以放行的
        // 没有jwt相当于匿名访问，若有一些接口是需要权限的，则不能访问这些接口
        if (StringUtils.isBlank(authorization) || !authorization.startsWith(ConstantUtil.BEARER)) {
            chain.doFilter(request, response);
            return;
        }
        authorization = authorization.substring(ConstantUtil.BEARER.length());
        Claims claim = JwtUtils.getClaimsByToken(authorization);
        if (claim == null) {
            throw new JwtException("token 异常");
        }
        if (JwtUtils.isTokenExpired(claim)) {
            throw new JwtException("token 已过期");
        }

        String username = claim.getSubject();
        // 获取用户的权限等信息
        UserDetailsModel userDetailsModel = userDetailService.loadUserByUsername(username);

        // 构建UsernamePasswordAuthenticationToken,这里密码为null，是因为提供了正确的JWT,实现自动登录
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, userDetailsModel.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);

        // 设置当前登录用户信息
        AspenContext context = new AspenContext();
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUserId(userDetailsModel.getUserId());
        userDetailsDTO.setUsername(userDetailsModel.getUsername());
        context.setUser(userDetailsDTO);
        context.setUserId(userDetailsModel.getUserId());
        AspenContextHolder.set(context);

        chain.doFilter(request, response);

    }
}
