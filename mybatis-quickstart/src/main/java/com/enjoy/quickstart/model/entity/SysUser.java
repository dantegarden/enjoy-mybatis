package com.enjoy.quickstart.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @description:
 * @author: lij
 * @create: 2019-09-26 14:34
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Integer deleteStatus;
    private Date createTime;
    private Date updateTime;
    private Long roleId;
    private Long orgId;
}
