package com.enjoy.myorm.orm.session;

import com.enjoy.myorm.orm.binding.MappedInterface;
import com.enjoy.myorm.orm.binding.MappedMethod;
import com.enjoy.myorm.orm.config.Configuration;
import com.enjoy.myorm.orm.config.MappedStatement;
import com.enjoy.myorm.orm.config.enums.MapperMethodEnum;
import com.enjoy.myorm.orm.datasource.ORMDataSource;
import com.enjoy.myorm.orm.util.PropertyParseUtil;
import com.enjoy.myorm.orm.util.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;

/**
 * @description: 工厂模式，用来生产SqlSession实例。
 *  作用有二：读取配置文件，解析信息，填充到configuration中； 生产SqlSession
 * @author: lij
 * @create: 2019-10-05 22:43
 */
public class SqlSessionFactory {

    private final Configuration configuration = new Configuration(); //全局唯一

    /**
     * mapper.xml默认存放的位置
     **/
    private static final String DEFAULT_MAPPER_CONFIG_LOCATION = "mapper";

    private SqlSessionFactory(){}

    public SqlSessionFactory(InputStream inputStream) {
        loadMainConfig(inputStream);
    }

    private void loadMainConfig(InputStream inputStream){
        //创建SAXReader对象
        SAXReader saxReader = new SAXReader();
        //通过read方法读取一个文件，转换成Document对象
        Document document = null;
        try {
            document = saxReader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取根节点元素对象<configuration>
        Element rootElement = document.getRootElement();
        //获取子节点<properties>标签
        Element properties = rootElement.element("properties");
        String propertisResource = properties.attribute("resource").getData().toString();
        //获取子节点<environments>标签
        Element environments = rootElement.element("environments");
        //获取找到真正发挥作用的environment
        String defaultEnvironmentId = environments.attribute("default").getData().toString();
        List<Element> environmentList = environments.elements("environment");
        Element defaultEnvironment = null;
        for (Element environment : environmentList) {
            String envId = environment.attribute("id").getData().toString();
            if(envId.equals(defaultEnvironmentId)){
                defaultEnvironment = environment;
                break;
            }
        }
        if(defaultEnvironment == null){
            if(CollectionUtils.isNotEmpty(environmentList)){
                defaultEnvironment = environmentList.get(0);
            }else{
                throw new RuntimeException("no environment found");
            }
        }
        //加载数据源
        Element dataSource = defaultEnvironment.element("dataSource");
        List<Element> dataSourceProperties = dataSource.elements("property");
        if(CollectionUtils.isNotEmpty(dataSourceProperties)){
            Map<String,String> environmentPropertiesMap = new HashMap<>();
            for (Element dataSourceProperty : dataSourceProperties) {
                String name = dataSourceProperty.attribute("name").getData().toString();
                String value = dataSourceProperty.attribute("value").getData().toString();
                environmentPropertiesMap.put(name, PropertyParseUtil.collect(value));
            }
            loadDataSource(propertisResource, environmentPropertiesMap);
        }
        //加载mapper.xml
        Element mappers = rootElement.element("mappers");
        String mappersSuffix = mappers.attribute("suffix").getData().toString();
        String mappersFolder = mappers.attribute("folder").getData().toString();
        mappersFolder = StringUtils.isNotBlank(mappersFolder)? mappersFolder: DEFAULT_MAPPER_CONFIG_LOCATION;
        loadMappersInfo(mappersFolder, mappersSuffix);
    }

    /**
     * 配置数据源
     *
     * @param
     * @return void
     **/
    private void loadDataSource(String propertisResource, Map<String,String> environmentPropertiesMap) {
        //加载数据库信息配置文件
        InputStream stream = SqlSessionFactory.class.getClassLoader().getResourceAsStream(propertisResource);
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将数据库配置信息写入configuration对象中
        configuration.setDriver(properties.get(environmentPropertiesMap.get("driver")).toString());
        configuration.setUrl(properties.get(environmentPropertiesMap.get("url")).toString());
        configuration.setUsername(properties.get(environmentPropertiesMap.get("username")).toString());
        configuration.setPassword(properties.get(environmentPropertiesMap.get("password")).toString());
        DataSource dataSource = ORMDataSource.getInstance(configuration);
        configuration.setDataSource(dataSource);
    }

    /**
     * 获取指定文件下的所有mapper.xml文件
     *
     * @param
     * @return void
     **/
    private void loadMappersInfo(String folder, String suffix) {
        URL resource = null;
        resource = SqlSessionFactory.class.getClassLoader().getResource(folder);
        //获取指定文件夹信息
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] mappers = file.listFiles();
            //遍历文件夹下所有的mapper.xml文件，解析后，注册到configuration中
            if(StringUtils.isNotBlank(suffix)){
                for (File mapper : mappers) {
                    if(mapper.getName().endsWith(suffix)){
                        loadMapper(mapper);
                    }
                }
            }else{ //没有后缀名，全部加载
                Arrays.asList(mappers).forEach(this::loadMapper);
            }

        }else{
            throw new RuntimeException("assigned folder: " + folder + " not found, maybe it's not a directory");
        }
    }

    /**
     * 对mapper.xml文件解析
     * 相当于XMLMapperConfig
     * @param mapper
     * @return void
     **/
    private void loadMapper(File mapper) {
        //创建SAXReader对象
        SAXReader saxReader = new SAXReader();
        //通过read方法读取一个文件，转换成Document对象
        Document document = null;
        try {
            document = saxReader.read(mapper);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取根节点元素对象<mapper>
        Element rootElement = document.getRootElement();
        //获取命名空间
        String namespace = rootElement.attribute("namespace").getData().toString();
        //TODO 根据namespace得到Mapper.java接口的信息
        loadMapperInterface(namespace);
        //获取子节点<select>标签
        List<Element> selects = rootElement.elements("select");
        //遍历select节点，将信息记录到MappedStatement对象，并登记到Configuration对象中
        for (Element element : selects) {
            String id = element.attribute("id").getData().toString();
            String sourceId = namespace + "." + id;
            MappedStatement statement = new MappedStatement.Builder()
                    .setNamespace(namespace)
                    .setResultType(element.attribute("resultType").getData().toString())
                    .setSourceId(sourceId)
                    .setSql(element.getData().toString())
                    .setMethodType(MapperMethodEnum.SELECT).build();
            //给MappedStatement对象赋值
            configuration.getStatementMap().put(sourceId, statement);
        }

        //获取子节点<insert>标签
        List<Element> inserts = rootElement.elements("insert");
        for (Element element : inserts) {
            String id = element.attribute("id").getData().toString();
            String sourceId = namespace + "." + id;
            MappedStatement statement = new MappedStatement.Builder()
                    .setNamespace(namespace)
                    .setSourceId(sourceId)
                    //.setParameterType(element.attribute("parameterType").getData().toString())
                    .setSql(element.getData().toString())
                    .setMethodType(MapperMethodEnum.INSERT).build();
            configuration.getStatementMap().put(sourceId, statement);
        }

        //获取子节点<update>标签
        List<Element> updates = rootElement.elements("update");
        for (Element element : updates) {
            String id = element.attribute("id").getData().toString();
            String sourceId = namespace + "." + id;
            MappedStatement statement = new MappedStatement.Builder()
                    .setNamespace(namespace)
                    .setSourceId(sourceId)
                    //.setParameterType(element.attribute("parameterType").getData().toString())
                    .setSql(element.getData().toString())
                    .setMethodType(MapperMethodEnum.UPDATE).build();
            configuration.getStatementMap().put(sourceId, statement);
        }

        //获取子节点<delete>标签
        List<Element> deletes = rootElement.elements("delete");
        for (Element element : updates) {
            String id = element.attribute("id").getData().toString();
            String sourceId = namespace + "." + id;
            MappedStatement statement = new MappedStatement.Builder()
                    .setNamespace(namespace)
                    .setSourceId(sourceId)
                    //.setParameterType(element.attribute("parameterType").getData().toString())
                    .setSql(element.getData().toString())
                    .setMethodType(MapperMethodEnum.DELETE).build();
            configuration.getStatementMap().put(sourceId, statement);
        }
    }

    /**分析对应的mapper接口**/
    private void loadMapperInterface(String namespace) {
        try {
            Class<?> clazz = Class.forName(namespace);
            MappedInterface mappedInterface = new MappedInterface(namespace);
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                String methodName = declaredMethod.getName();
                if(ReflectUtil.isDefaultMethod(declaredMethod)){
                    MappedMethod mappedMethod = new MappedMethod(declaredMethod, namespace);
                    mappedInterface.getMethods().put(methodName, mappedMethod);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
