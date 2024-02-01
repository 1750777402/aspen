package com.aspen.module.user.dto;

import com.aspen.core.foundation.base.BaseDTO;
import lombok.Data;

@Data
public class UserDTO extends BaseDTO {

    private Long id;
    private String name;

}
