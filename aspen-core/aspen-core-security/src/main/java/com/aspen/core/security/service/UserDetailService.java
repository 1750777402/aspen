package com.aspen.core.security.service;

import com.aspen.core.security.model.UserDetailsModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author jingchuan
 */
public interface UserDetailService extends UserDetailsService {

    UserDetailsModel loadUserByUsername(String username) throws UsernameNotFoundException;

}
