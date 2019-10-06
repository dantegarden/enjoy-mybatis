package com.enjoy.myorm;

import com.enjoy.myorm.mapper.SysUserMapper;
import com.enjoy.myorm.model.entity.SysUser;
import com.enjoy.myorm.orm.io.Resources;
import com.enjoy.myorm.orm.session.SqlSession;
import com.enjoy.myorm.orm.session.SqlSessionFactory;
import com.enjoy.myorm.orm.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description:
 * @author: lij
 * @create: 2019-10-05 23:59
 */
public class MyOrmDemo {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("orm-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        System.out.println(sqlSession);

        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUser sysUser = userMapper.selectByPrimaryKey(10019L);
        System.out.println(sysUser);

        int ret = userMapper.updateUsername("lkk-orm", 10019L);
        System.out.println(ret);
    }
}
