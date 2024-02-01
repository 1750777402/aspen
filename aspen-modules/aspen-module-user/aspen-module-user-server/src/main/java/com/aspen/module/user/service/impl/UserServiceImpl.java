package com.aspen.module.user.service.impl;

import com.aspen.core.foundation.exception.BizException;
import com.aspen.core.foundation.utils.ConvertUtils;
import com.aspen.module.user.mapper.UserMapper;
import com.aspen.module.user.dto.UserDTO;
import com.aspen.module.user.entity.UserEntity;
import com.aspen.module.user.service.UserService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 *
 * @author jingchuan
 */
@DubboService
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO getUser(Long id) {
        if (id == null) {
            throw new BizException("参数错误");
        }
        UserEntity userEntity = userMapper.selectOneById(id);
        if (userEntity != null) {
            return ConvertUtils.sourceToTarget(userEntity, UserDTO.class);
        }
        return null;
    }
}
