package com.enjoy.myorm.mapper;

import com.enjoy.myorm.model.entity.SysUser;

import java.util.List;

/**
 * @description:
 * @author: lij
 * @create: 2019-10-05 22:34
 */
public interface SysUserMapper {

    SysUser selectByPrimaryKey(Long id);

    List<SysUser> selectAll();

    int updateUsername(String username, Long id);
}
