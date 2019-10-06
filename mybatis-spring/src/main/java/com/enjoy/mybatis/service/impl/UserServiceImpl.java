package com.enjoy.mybatis.service.impl;

import com.enjoy.mybatis.mapper.SysUserMapper;
import com.enjoy.mybatis.model.bo.TUser;
import com.enjoy.mybatis.model.entity.SysUser;
import com.enjoy.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: lij
 * @create: 2019-09-30 15:33
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public SysUser getUser(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public int saveUser(SysUser user) {
        if(user.getId()!=null){
           return userMapper.updateIfOper(user);
        }
        return userMapper.insertSelective(user);
    }
}
