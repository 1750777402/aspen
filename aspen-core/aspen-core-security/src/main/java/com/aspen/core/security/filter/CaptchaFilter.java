package com.aspen.core.security.filter;

import com.aspen.core.cache.utils.RedisUtil;
import com.aspen.core.security.constants.ConstantUtil;
import com.aspen.core.security.exception.CaptchaException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    /**
     * 自定义处理逻辑
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String url = httpServletRequest.getRequestURI();
        if (ConstantUtil.LOGIN_URL.equals(url) && ConstantUtil.POST_METHOD.equals(httpServletRequest.getMethod())) {
            // 校验验证码
            validate(httpServletRequest);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 校验验证码逻辑
     */
    private void validate(HttpServletRequest httpServletRequest) {
        String code = httpServletRequest.getParameter("code");
        String key = httpServletRequest.getParameter("userKey");

        if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
            throw new CaptchaException("验证码错误");
        }

        if (!code.equals(RedisUtil.get(ConstantUtil.CAPTCHA_KEY + key))) {
            throw new CaptchaException("验证码错误");
        }

        // 若验证码正确，执行以下语句
        // 一次性使用
        RedisUtil.remove(ConstantUtil.CAPTCHA_KEY + key);
    }
}
