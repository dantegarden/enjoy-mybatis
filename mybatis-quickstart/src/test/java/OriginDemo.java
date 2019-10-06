import com.enjoy.quickstart.mapper.SysUserMapper;
import com.enjoy.quickstart.model.entity.SysUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description: mybatis动态代理原理
 * @author: lij
 * @create: 2019-10-03 17:30
 */
public class OriginDemo {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        inputStream.close();
    }

    /**动态代理的本来面目**/
    @Test
    public void testOriginalOperation(){
        //获得一个连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //namespace.id定位sql语句，传入参数
        SysUser sysUser = sqlSession.selectOne("com.enjoy.quickstart.mapper.SysUserMapper.selectByPrimaryKey", 10019L);
        System.out.println(sysUser);
    }
}
