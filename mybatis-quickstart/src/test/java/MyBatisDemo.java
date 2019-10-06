import com.enjoy.quickstart.generator.MyBatisGenerator;
import com.enjoy.quickstart.mapper.SysOrgMapper;
import com.enjoy.quickstart.model.bo.TUser;
import com.enjoy.quickstart.model.entity.SysOrg;
import com.enjoy.quickstart.model.entity.SysOrgExample;
import com.enjoy.quickstart.model.entity.SysUser;
import com.enjoy.quickstart.mapper.SysUserMapper;
import com.enjoy.quickstart.model.querybean.SysUserUsernameDeleteStatusQueryBean;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @description:
 * @author: lij
 * @create: 2019-09-27 11:53
 */
public class MyBatisDemo {
    private SqlSessionFactory sqlSessionFactory;

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

    /**自动映射*/
    @Test
    public void testAutoMapping(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUser user = userMapper.selectByPrimaryKey(10019L);
        System.out.println(user);
    }

    /**多个入参*/
    @Test
    public void testManyParameter(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        Map<String,Object> param = Maps.newHashMap();
        param.put("username", "l");
        param.put("deleteStatus", 1);
        List<SysUser> sysUserList = userMapper.selectByUsernameAndDeleteStatus1(param);
        sysUserList.forEach(System.out::println);
        System.out.println("---------------");

        sysUserList = userMapper.selectByUsernameAndDeleteStatus2("l",1);
        sysUserList.forEach(System.out::println);
        System.out.println("---------------");

        SysUserUsernameDeleteStatusQueryBean queryBean = new SysUserUsernameDeleteStatusQueryBean("l",1);
        sysUserList = userMapper.selectByUsernameAndDeleteStatus3(queryBean);
        sysUserList.forEach(System.out::println);
        System.out.println("---------------");

    }

    /**测试主键自增**/
    @Test
    public void testGenerateId(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUser sysUser = new SysUser();
        sysUser.setUsername("test_mybatis").setNickname("test_mybatis")
                .setCreateTime(new Date()).setUpdateTime(new Date())
                .setPassword("12312312")
                .setDeleteStatus(1);
        int ret = userMapper.insert(sysUser);
        System.out.println("插入成功，结果：" + ret + " ，id：" + sysUser.getId());

        SysUser sysUser1 = new SysUser();
        sysUser1.setUsername("test_mybatis1").setNickname("test_mybatis1")
                .setCreateTime(new Date()).setUpdateTime(new Date())
                .setPassword("12312312")
                .setDeleteStatus(1);
        ret = userMapper.insert1(sysUser1);
        System.out.println("插入成功，结果：" + ret + " ，id：" + sysUser1.getId());

        SysUser sysUser2 = new SysUser();
        sysUser2.setUsername("test_mybatis2").setNickname("test_mybatis2")
                .setCreateTime(new Date()).setUpdateTime(new Date())
                .setPassword("12312312")
                .setDeleteStatus(1);
        ret = userMapper.insert2(sysUser2);
        System.out.println("插入成功，结果：" + ret + " ，id：" + sysUser2.getId());

        sqlSession.commit(); //需要提交
        System.out.println("重新看Before的id：" + sysUser.getId());

    }

    /**占位符测试**/
    @Test
    public void testSymbol(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        String inCol = "id, username, password, nickname";
        String tableName = "sys_user";
        Integer deleteStatus = 1;
        String orderStr = "create_time, id";
        List<SysUser> results = userMapper.selectBySymbol(inCol, tableName, orderStr, deleteStatus);
        results.forEach(System.out::println);
    }

    /**测试注解方式**/
    @Test
    public void testMybatisAnnotations(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUser sysUser = new SysUser();
        sysUser.setUsername("test_mybatis_anno").setNickname("test_mybatis_anno")
                .setCreateTime(new Date()).setUpdateTime(new Date())
                .setPassword("12312312")
                .setDeleteStatus(1);
        int ret = userMapper.insertOne(sysUser);
        System.out.println("插入成功，结果：" + ret + " ，id：" + sysUser.getId());
        sqlSession.commit();

        List<SysUser> results = userMapper.selectByUsername("l");
        results.forEach(System.out::println);
    }

    /**测试select+if标签*/
    @Test
    public void testSelectIfOper(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        String username = "l";
        Integer deleteStatus = 1;
        List<SysUser> sysUsers = userMapper.selectIfOper(username, deleteStatus);
        sysUsers.forEach(System.out::println);

        sysUsers = userMapper.selectIfOperWhere(username, deleteStatus);
        sysUsers.forEach(System.out::println);
    }

    /**
     *  测试select+choose
     */
    @Test
    public void testSelectChooseOper(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        String username = "l";
        Integer deleteStatus = 1;
        List<SysUser> sysUsers = userMapper.selectChooseOper(username, deleteStatus);
        sysUsers.forEach(System.out::println);
    }

    /**测试update+if标签*/
    @Test
    public void testUpdateIfOper(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true); //开启自动提交
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUser user = new SysUser();
        user.setUpdateTime(new Date());
        user.setDeleteStatus(2);
        user.setId(10019L);
        //int ret = userMapper.updateIfOper(user);
        int ret = userMapper.updateIfOperWhere(user);
        System.out.println(ret);
    }

    /**测试insert+if标签*/
    @Test
    public void testInsertIfOper(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true); //开启自动提交
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUser user = new SysUser();
        user.setUpdateTime(new Date());
        user.setUsername("insertIfOper").setPassword("12312312").setNickname("insertIfOper");
        user.setDeleteStatus(2);
        user.setCreateTime(new Date()).setUpdateTime(new Date());
        int ret = userMapper.insertSelective(user);
        System.out.println(ret +" " + user.getId());
    }

    /**测试foreach标签**/
    @Test
    public void testForeach4In(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true); //开启自动提交
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        String[] names = new String[]{"lijing","lkk"};
        List<SysUser> users = userMapper.selectForeach4In(names);
        users.forEach(System.out::println);

        List<SysUser> userList = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            String username = "insertIfOper"+i;
            SysUser user = new SysUser();
            user.setUpdateTime(new Date());
            user.setUsername(username).setPassword("zxczxc").setNickname(username);
            user.setDeleteStatus(2);
            user.setCreateTime(new Date()).setUpdateTime(new Date());
            userList.add(user);
        }
        userMapper.insertForeach4In(userList);
    }

    /**测试批量操作**/
    @Test
    public void testBatchExecutor(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true); //自动提交事务，但是失灵
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        List<SysUser> list = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            String username = "batchExecutor"+i;
            SysUser user = new SysUser();
            user.setUpdateTime(new Date());
            user.setUsername(username).setPassword("12312321").setNickname(username);
            user.setDeleteStatus(2);
            user.setCreateTime(new Date()).setUpdateTime(new Date());
            list.add(user);

            userMapper.insertSelective(user); //执行完这句，数据库不会有数据
        }
        sqlSession.commit(); //还是要手动提交
        list.forEach(System.out::println);
    }

    /**
     * 通过java程序执行代码生成器
     * */
    @Test
    public void testMybatisGenerator(){
        MyBatisGenerator.generate(true);
    }

    /**使用Example
     * 此处用的SysOrg是生成器生成的
     * */
    @Test
    public void testQueryExample(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        SysOrgMapper orgMapper = sqlSession.getMapper(SysOrgMapper.class);
        SysOrgExample example = new SysOrgExample();
        SysOrgExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteStatusEqualTo(true);
        criteria.andOrgNameLike("%集团%");
        List<SysOrg> sysOrgs = orgMapper.selectByExample(example);
        sysOrgs.forEach(System.out::println);

        SysOrg org = new SysOrg();
        org.setOrgName("山水集团");
        org.setOrgCode("00005");
        org.setPid(1L);
        orgMapper.insertSelective(org);
        System.out.println(org.getId()); //拿不到id
    }

    /**一对一*/
    @Test
    public void testOne2One(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        List<TUser> users;
//        users = userMapper.selectUserOrg();
//        users.forEach(System.out::println);
        users = userMapper.selectUserOrg2();
        System.out.println("-------------------延迟加载（按需加载）---------------");
        users.forEach(System.out::println);
    }

    /**一对多*/
    @Test
    public void testOne2Many(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        List<TUser> users;
        users = userMapper.selectUserRelations();
        users.forEach(System.out::println);
//        users = userMapper.selectUserRelations2();
//        System.out.println("-------------------延迟加载（按需加载）---------------");
//        users.forEach(System.out::println);
    }

    /**多对多*/
    @Test
    public void testMany2Many(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        List<TUser> users;
//        users = userMapper.selectUserRoles();
//        users.forEach(System.out::println);
        users = userMapper.selectUserRoles2();
        System.out.println("-------------------延迟加载（按需加载）---------------");
        users.forEach(System.out::println);
    }

    /**鉴别器*/
    @Test
    public void testDiscriminator(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        List<TUser> users;
        users = userMapper.selectDiscriminator();
        users.forEach(System.out::println);
    }

    /**测试一级缓存*/
    @Test
    public void testMybatisCacheLv1(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        Map<String,Object> params = new HashMap<> ();
        params.put("deleteStatus", 1);
        params.put("username", "l");
        List<SysUser> users;
        System.out.println("=================第一次查询================");
        users = userMapper.selectByUsernameAndDeleteStatus1(params);  //没有缓存，走库
        System.out.println("=================第二次查询================");
        users = userMapper.selectByUsernameAndDeleteStatus1(params); //完全一样的方法再调一遍，不会走库
        System.out.println("=================第三次查询================");
        params.put("username", "lk");
        users = userMapper.selectByUsernameAndDeleteStatus1(params); //完全一样的方法，修改参数，走库
        System.out.println("=================第四次查询================");
        users = userMapper.selectByUsernameAndDeleteStatus2("lk", 1); //方法名一样，参数和sql一样，走库
        System.out.println("=================第五次查询================");
        sqlSession.close();
        sqlSession = sqlSessionFactory.openSession(true);
        userMapper = sqlSession.getMapper(SysUserMapper.class);
        users = userMapper.selectByUsernameAndDeleteStatus2("lk", 1); //关闭sqlsession，会清空缓存，走库
        System.out.println("=================第六次查询================");
        SysUser sysUser = new SysUser();
        sysUser.setId(10010L);
        sysUser.setUpdateTime(new Date());
        int ret = userMapper.updateIfOper(sysUser);
        users = userMapper.selectByUsernameAndDeleteStatus2("lk", 1); //修改一条数据，致使缓存被清空，再用相同的参数跑相同的方法，还会走库
    }

    /**测试二级缓存
     * 需要打开总的二级缓存开关 cacheEnabled=true
     * 并且打开mapper.xml里的 <cache eviction="FIFO" flushInterval="60000" size="512" readOnly="true"></cache> 注释
     * */
    @Test
    public void testMybatisCacheLv2(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        Map<String,Object> params = new HashMap<> ();
        params.put("deleteStatus", 1);
        params.put("username", "l");
        List<SysUser> users;
        System.out.println("=================第一次查询================");
        users = userMapper.selectByUsernameAndDeleteStatus1(params);  //没有缓存，走库
        sqlSession.close();
        System.out.println("=================第二次查询================");
        sqlSession = sqlSessionFactory.openSession(true);
        userMapper = sqlSession.getMapper(SysUserMapper.class);
        users = userMapper.selectByUsernameAndDeleteStatus1(params); //重开sqlSession，完全一样的方法再调一遍，不会走库
        sqlSession.close();
    }
}
