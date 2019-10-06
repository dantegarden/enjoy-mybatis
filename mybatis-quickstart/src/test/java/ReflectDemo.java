import com.enjoy.quickstart.model.bo.TUser;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @description: mybatis反射测试
 * @author: lij
 * @create: 2019-10-02 18:15
 */
public class ReflectDemo {
    @Test
    public void reflectionTest(){
        //对象的创建和初始化
        ObjectFactory objectFactory = new DefaultObjectFactory();
        TUser user = objectFactory.create(TUser.class);
        ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
        ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        MetaObject metaObject = MetaObject.forObject(user, objectFactory, objectWrapperFactory, reflectorFactory);

        //使用Reflector读取类信息
        Reflector findForClass = reflectorFactory.findForClass(TUser.class);
        Constructor<?> defaultConstructor = findForClass.getDefaultConstructor();  //基础构造器
        String[] getablePropertyNames = findForClass.getGetablePropertyNames();
        String[] setablePropertyNames = findForClass.getSetablePropertyNames();
        System.out.println(defaultConstructor.getName());
        System.out.println("------------getablePropertyNames----------");
        Arrays.asList(getablePropertyNames).forEach(System.out::println);
        System.out.println("------------setablePropertyNames----------");
        Arrays.asList(setablePropertyNames).forEach(System.out::println);

        //使用ObjectWrapper读取对象信息，并对对象属性进行赋值
        TUser userTemp = new TUser();
        ObjectWrapper wrapper = new BeanWrapper(metaObject, userTemp);
        String[] getterNames = wrapper.getGetterNames();
        String[] setterNames = wrapper.getSetterNames();
        System.out.println("------------getterNames----------");
        Arrays.asList(getterNames).forEach(System.out::println);
        System.out.println("------------setterNames----------");
        Arrays.asList(setterNames).forEach(System.out::println);
        System.out.println("---------------------------------");
        //通过objectWrapper给对象赋值
        PropertyTokenizer prop = new PropertyTokenizer("username");
        wrapper.set(prop, "dante");
        System.out.println(userTemp.getUsername());
    }

    @Test
    public void testMapping(){

        ObjectFactory objectFactory = new DefaultObjectFactory();
        TUser user = objectFactory.create(TUser.class);
        ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
        ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        MetaObject metaObject = MetaObject.forObject(user, objectFactory, objectWrapperFactory, reflectorFactory);


        //手动模拟一下从数据查出结果集如何映射成实体对象
        Map<String,Object> resultSet = new HashMap<>(); //模拟数据库结果集
        resultSet.put("id", 999L);
        resultSet.put("username", "dante");
        resultSet.put("password", "123123");
        resultSet.put("delete_status", 1);
        resultSet.put("create_time", new Date());
        //映射关系
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("id", "id");
        resultMap.put("username", "username");
        resultMap.put("password", "password");
        resultMap.put("deleteStatus", "delete_status");
        resultMap.put("createTime","create_time");

        //使用反射工具
        ObjectWrapper objectWrapper = metaObject.getObjectWrapper();
        for (Map.Entry<String, String> resultMapEntry : resultMap.entrySet()) {
            String propertyName = resultMapEntry.getKey();
            String columnName = resultMapEntry.getValue();
            Object data = resultSet.get(columnName);
            PropertyTokenizer propertyTokenizer = new PropertyTokenizer(propertyName);
            objectWrapper.set(propertyTokenizer, data);
        }
        System.out.println(user);
    }
}
