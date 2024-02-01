package com.aspen.core.service.base;

import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -4862298523724602158L;

    @Column(isLogicDelete = true)
    private Integer isDeleted;

    private Long created;

    private Long updated;

}
