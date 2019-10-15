package com.demo.druid;

import com.demo.mybaits.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 使用mybatis
 */
public class DruidMybatisTest {

    public static void main(String[] args) throws IOException {
        // 指定全局配置文件
        String resource = "druid-mybatis.xml";
        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 构建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            // 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
            // 第二个参数：指定传入sql的参数：这里是用户id
            User user = sqlSession.selectOne("MyMapper.selectUser", 1);
            //注意：此处有陷阱，如果做了更新、插入或删除操作，必须使用：
            //session.commit();
            System.out.println(user);
        } finally {
            sqlSession.close();
        }
    }
}
