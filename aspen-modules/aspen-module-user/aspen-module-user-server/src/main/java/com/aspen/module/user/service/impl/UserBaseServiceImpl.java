package com.aspen.module.user.service.impl;

import com.aspen.core.foundation.base.UserDetailsDTO;
import com.aspen.microservices.user.UserBaseService;
import com.aspen.module.user.dto.UserDTO;
import com.aspen.module.user.service.UserService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserBaseServiceImpl implements UserBaseService {

    @Resource
    private UserService userService;

    @Override
    public UserDetailsDTO getUserDetailsDTO(Long userId) {
        UserDTO userDTO = userService.getUser(userId);
        if (userDTO != null) {
            UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
            userDetailsDTO.setUserId(userDTO.getId());
            userDetailsDTO.setUsername(userDTO.getName());
            return userDetailsDTO;
        }
        return null;
    }
}
