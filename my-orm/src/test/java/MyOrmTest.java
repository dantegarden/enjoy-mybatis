import com.enjoy.myorm.mapper.SysUserMapper;
import com.enjoy.myorm.model.entity.SysUser;
import com.enjoy.myorm.orm.session.SqlSession;
import com.enjoy.myorm.orm.session.SqlSessionFactory;
import com.enjoy.myorm.orm.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @description:
 * @author: lij
 * @create: 2019-10-06 10:29
 */
public class MyOrmTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("orm-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        List<SysUser> sysUsers = sysUserMapper.selectAll();
        sysUsers.forEach(System.out::println);
    }
}
