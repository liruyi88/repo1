package com.lagou.test;

import com.lagou.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 快速入门测试方法
 */
public class MybatisTest {
    @Test
    public void testFindAll() throws IOException {
        //1.加载配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //2.获取sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //3.获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4.执行sql  参数:statementid:namespace.id
        List<User> users = sqlSession.selectList("UserMapper.findAll");
        //5.遍历结果
        for (User user : users) {
            System.out.println(user);
        }
        //6.关闭资源
        sqlSession.close();
    }

    /**
     * 测试新增用户
     * @throws IOException
     */
    @Test
    public void testSaveUser() throws IOException {
        //1.加载配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //2.获取sqlSessionFactory工厂对象
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //3.获取sqlSession会话对象
        SqlSession sqlSession = sessionFactory.openSession();
        //创建User对象
        User user = new User();
        user.setUsername("阿黎");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("眉山仁寿");
        //4.执行sql
        int row = sqlSession.insert("UserMapper.saveUser",user);
        //提交事务
        sqlSession.commit();
    }
    @Test
    public void testUpdateUser() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(5);
        user.setUsername("谢广坤");
        user.setSex("男");
        user.setBirthday(new Date());
        user.setAddress("农村爱情!");
        sqlSession.update("UserMapper.updateUser",user);
        //是否关闭手动提交
        sqlSession.commit(true);
    }
    /**
     * 测试删除用户
     */
    @Test
    public void testDeleteUser() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int row = sqlSession.delete("UserMapper.deleteUser", 3);
        //手动提交事务
        sqlSession.commit();
        if (row > 0){
            System.out.println("删除数据成功!");
        }else {
            System.out.println("删除数据失败!");
        }
    }
}
