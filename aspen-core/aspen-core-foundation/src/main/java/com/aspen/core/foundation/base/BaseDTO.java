package com.aspen.core.foundation.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 7894305848575111583L;

    private Integer isDeleted;

    private Long created;

    private Long updated;

}
