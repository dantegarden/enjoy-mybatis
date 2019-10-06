package com.enjoy.jdbc.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @description:
 * @author: lij
 * @create: 2019-09-26 14:34
 */
@Data
@Accessors(chain = true)
public class SysUser {
    private Long id;
    private Date createTime;
    private Date updateTime;
    private Integer deleteStatus;
    private String nickname;
    private String password;
    private String username;
    private Long roleId;
    private Long orgId;
}
