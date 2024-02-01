package com.aspen.module.user.entity;

import com.aspen.core.service.base.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table("user")
public class UserEntity extends BaseEntity {

    @Id(keyType = KeyType.Auto)
    private Long id;
    private String name;

}
