package com.demo.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 使用Druid连接池和原生jdbc
 */
public class DruidJDBCTest {

    public static void main(String[] args) throws IOException, SQLException {
        // 读取配置文件
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = DruidJDBCTest.class.getClassLoader().getResourceAsStream("druid.properties");
        // 使用properties对象加载输入流
        properties.load(in);

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.configFromPropety(properties);

        DruidPooledConnection connection = dataSource.getConnection();

        // 获取statement，preparedStatement， 问题：sql和java代码耦合
        String sql = "select * from tb_user where id=?";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        // 设置参数， 问题1：参数类型需要手动判断，问题2：需要判断下标，问题3：手动设置参数
        prepareStatement.setLong(1, 1l);
        // 执行查询
        ResultSet rs = prepareStatement.executeQuery();
        // 处理结果集，问题1：结果集中的数据类型需要手动判断，问题2：下标或列名需要手动判断
        while (rs.next()) {
            System.out.println(rs.getString("user_name"));
            System.out.println(rs.getString("name"));
            System.out.println(rs.getInt("age"));
            System.out.println(rs.getDate("birthday"));
        }

        connection.close();
    }
}
