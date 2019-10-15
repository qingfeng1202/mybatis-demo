package com.demo.jdbc;

import java.sql.*;

/**
 * 原生jdbc代码
 */
public class JDBCTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 加载驱动, 问题1：每次都要加载连接，问题2：驱动名称硬编码
        Class.forName("com.mysql.jdbc.Driver");
        // 获取连接， 问题1：每次都要获取连接，问题2：连接信息硬编码
        String url = "jdbc:mysql://192.168.1.31:3306/test";
        String user = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, user, password);

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
        // 关闭连接，释放资源，问题：每次都要打开或者关闭连接，浪费资源
        rs.close();
        prepareStatement.close();
        connection.close();
    }

}
