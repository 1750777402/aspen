package com.aspen.module.auth.server.controller;

import com.aspen.core.foundation.base.UserDetailsDTO;
import com.aspen.core.foundation.context.AspenContext;
import com.aspen.core.foundation.context.AspenContextHolder;
import com.aspen.core.foundation.utils.AspenResponse;
import com.aspen.core.security.constants.ConstantUtil;
import com.aspen.core.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author jingchuan
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class LoginController {

    @PostMapping("/login")
    public AspenResponse<String> login() {
        String jwtStr = JwtUtils.generateToken("1");
        return AspenResponse.success(ConstantUtil.BEARER + jwtStr);
    }

    @GetMapping("/test")
    public AspenResponse<String> test(@RequestParam("param") String param) {
        UserDetailsDTO user = AspenContextHolder.get().getUser();
        log.info("当前用户：{}", user);
        return AspenResponse.success("user:" + user.getUsername() + " " + param);
    }
}
