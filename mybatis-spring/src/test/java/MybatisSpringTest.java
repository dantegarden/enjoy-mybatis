import com.enjoy.mybatis.model.entity.SysUser;
import com.enjoy.mybatis.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @description:
 * @author: lij
 * @create: 2019-09-30 15:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisSpringTest {
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private UserService userService;

    @Before
    public void init(){
        String resource = "mybatis-config.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSpringMybatis(){
        System.out.println(userService.getUser(10019L));
        SysUser user = new SysUser();
        user.setId(10019L);
        user.setUpdateTime(new Date());
        int ret = userService.saveUser(user);
        System.out.println(ret);
    }
}
