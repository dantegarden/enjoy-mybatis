import com.enjoy.quickstart.model.entity.SysUser;
import com.enjoy.quickstart.mapper.SysUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description:
 * @author: lij
 * @create: 2019-09-26 15:30
 */
public class QuickStartDemo {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
       String resource = "mybatis-config.xml";
       InputStream inputStream = Resources.getResourceAsStream(resource);
       sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
       inputStream.close();
    }

    @Test
    public void quickStart(){
        //获得一个连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //拿到mapper的实现类
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(10019L);
        System.out.println(sysUser);
    }

}
