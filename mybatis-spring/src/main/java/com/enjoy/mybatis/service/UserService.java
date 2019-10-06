package com.enjoy.mybatis.service;

import com.enjoy.mybatis.model.bo.TUser;
import com.enjoy.mybatis.model.entity.SysUser;

public interface UserService {
    SysUser getUser(Long id);

    int saveUser(SysUser user);
}
