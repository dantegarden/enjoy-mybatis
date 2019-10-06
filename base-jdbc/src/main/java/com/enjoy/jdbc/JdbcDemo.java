package com.enjoy.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.enjoy.jdbc.entity.SysUser;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 纯jdbc连接数据库
 * @author: lij
 * @create: 2019-09-26 14:26
 */
@Slf4j
public class JdbcDemo {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ssm?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String DB_USERNAME = "test";
    private static final String DB_PWD = "123456";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static DruidDataSource dataSource;

    static {
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PWD);
    }

    /**查询*/
    public List<SysUser> queryPreparedStatementDemo(){
        List<SysUser> result = Lists.newArrayList();

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //注册驱动
//            Class.forName(DB_DRIVER);

            //打开连接
//            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PWD);

            conn = dataSource.getConnection();

            //创建查询
            String sql = "SELECT * FROM sys_user u WHERE u.delete_status = ? ";
            stmt = conn.prepareStatement(sql);
            //设置参数
            stmt.setInt(1, 1);
            //执行查询
            ResultSet rs = stmt.executeQuery();
            //遍历结果集
            while(rs.next()){
                SysUser user = new SysUser();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setNickname(rs.getString("nickname"));
                user.setPassword(rs.getString("password"));
                user.setDeleteStatus(rs.getInt("delete_status"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                result.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null){
                    stmt.close();
                }
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**更新*/
    public void updatePreparedStatementDemo(){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
//            Class.forName(DB_DRIVER);
//            conn = DriverManager.getConnection(DB_DRIVER, DB_USERNAME, DB_PWD);
            conn = dataSource.getConnection();
            //关闭自动提交
            conn.setAutoCommit(false);
            //创建语句
            String sql = "UPDATE sys_user u SET delete_status = ? WHERE u.id = ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 1);
            stmt.setLong(2,10019L);
            //执行语句
            int ret = stmt.executeUpdate();
            log.info("record changed: {}", ret);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if(stmt!=null){
                    stmt.close();
                }
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**批量操作*/
    public void executeBatch(){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
//            Class.forName(DB_DRIVER);
//            conn = DriverManager.getConnection(DB_DRIVER, DB_USERNAME, DB_PWD);
            conn = dataSource.getConnection();
            //关闭自动提交
            conn.setAutoCommit(false);
            //创建语句
            String sql = "UPDATE sys_user u SET delete_status = ? WHERE u.id = ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 2);
            stmt.setLong(2,10019L);
            stmt.addBatch();
            stmt.setInt(1, 2);
            stmt.setLong(2,10020L);
            stmt.addBatch();
            //执行语句
            int[] rets = stmt.executeBatch();
            log.info("record changed: {}", Arrays.toString(rets));
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if(stmt!=null){
                    stmt.close();
                }
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JdbcDemo jdbcDemo = new JdbcDemo();
        List<SysUser> sysUsers = jdbcDemo.queryPreparedStatementDemo();
        sysUsers.forEach(System.out::println);
        jdbcDemo.updatePreparedStatementDemo();
        jdbcDemo.executeBatch();
    }
}
