package com.aspen.core.security.service.impl;

import com.aspen.core.foundation.base.UserDetailsDTO;
import com.aspen.core.security.model.UserDetailsModel;
import com.aspen.core.security.service.UserDetailService;
import com.aspen.microservices.user.UserBaseService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @DubboReference
    private UserBaseService userBaseService;

    @Override
    public UserDetailsModel loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetailsDTO userDetailsDTO = userBaseService.getUserDetailsDTO(Long.parseLong(username));
        if (userDetailsDTO == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        UserDetailsModel userDetailsModel = new UserDetailsModel();
        userDetailsModel.setUserId(userDetailsDTO.getUserId());
        userDetailsModel.setUsername(userDetailsDTO.getUsername());
        return userDetailsModel;
    }

}
