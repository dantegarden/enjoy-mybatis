package com.enjoy.quickstart.mapper;

import com.enjoy.quickstart.model.bo.TUser;
import com.enjoy.quickstart.model.entity.SysUser;
import com.enjoy.quickstart.model.querybean.SysUserUsernameDeleteStatusQueryBean;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lij
 * @create: 2019-09-26 15:27
 */
public interface SysUserMapper {
    //----------------------XML方式-----------------------------

    SysUser selectByPrimaryKey(Long id);

    List<SysUser> selectByUsernameAndDeleteStatus1(Map<String,Object> params);

    List<SysUser> selectByUsernameAndDeleteStatus2(@Param("username") String username, @Param("deleteStatus") Integer deleteStatus);

    List<SysUser> selectByUsernameAndDeleteStatus3(SysUserUsernameDeleteStatusQueryBean queryBean);

    List<SysUser> selectBySymbol(String inCol, String tableName, String orderStr, Integer deleteStatus);

    List<SysUser> selectIfOper(String username, Integer deleteStatus);
    List<SysUser> selectIfOperWhere(String username, Integer deleteStatus);
    List<SysUser> selectChooseOper(String username, Integer deleteStatus);
    List<SysUser> selectForeach4In(String[] names);

    int insert(SysUser sysUser);
    int insert1(SysUser sysUser);
    int insert2(SysUser sysUser);
    int insertSelective(SysUser sysUser);
    int insertForeach4In(List<SysUser> users);

    int updateIfOper(SysUser sysUser);
    int updateIfOperWhere(SysUser sysUser);

    int deleteByPrimaryKey(Long id);

    /***关联操作**/
    List<TUser> selectUserOrg();
    List<TUser> selectUserOrg2();

    List<TUser> selectUserRelations();
    List<TUser> selectUserRelations2();

    List<TUser> selectUserRoles();
    List<TUser> selectUserRoles2();
    /**鉴别器**/
    List<TUser> selectDiscriminator();
    //----------------------注解方式-----------------------------

    @Results(id="myResultMap", value={
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "deleteStatus", column = "delete_status"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "orgId", column = "org_id")
    })
    @Select({"select id, username, password, nickname, delete_status, create_time, update_time, role_id, org_id " +
            "from sys_user u where u.username like CONCAT('%', #{username}, '%')"})
    List<SysUser> selectByUsername(@Param("username") String username);

    @ResultMap("myResultMap")
    @Select("select * from sys_user")
    List<SysUser> selectAll();

    @Insert("insert into sys_user (id, username, password, nickname, delete_status, create_time, update_time) " +
            "values( #{id},#{username},#{password},#{nickname},#{deleteStatus},#{createTime,jdbcType=DATE},#{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOne(SysUser sysUser);


}
