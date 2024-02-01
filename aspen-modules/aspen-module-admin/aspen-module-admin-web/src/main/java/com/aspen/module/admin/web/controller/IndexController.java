package com.aspen.module.admin.web.controller;

import com.aspen.core.foundation.exception.BizException;
import com.aspen.core.foundation.utils.AspenResponse;
import com.aspen.module.user.dto.UserDTO;
import com.aspen.module.user.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

    @DubboReference
    private UserService userService;

    @GetMapping("")
    public AspenResponse<UserDTO> index(@RequestParam(value = "id", required = false) Long id) {
        if (id == null) {
            throw new BizException("参数错误");
        }

        return AspenResponse.success(userService.getUser(id));
    }

}
