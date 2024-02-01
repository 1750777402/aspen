package com.aspen.microservices.user;

import com.aspen.core.foundation.base.UserDetailsDTO;

public interface UserBaseService {

    UserDetailsDTO getUserDetailsDTO(Long userId);

}
