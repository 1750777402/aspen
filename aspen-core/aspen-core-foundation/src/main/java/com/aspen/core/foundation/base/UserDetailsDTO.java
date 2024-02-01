package com.aspen.core.foundation.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jingchuan
 */
@Data
public class UserDetailsDTO implements Serializable {

    private static final long serialVersionUID = -7700768142799377857L;

    private Long userId;

    private String username;


}
