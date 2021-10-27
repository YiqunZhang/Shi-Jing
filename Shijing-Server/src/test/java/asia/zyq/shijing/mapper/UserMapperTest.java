package asia.zyq.shijing.mapper;

import asia.zyq.shijing.beans.ParamsTwoInt;
import asia.zyq.shijing.pojo.User;
import asia.zyq.shijing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.List;
import java.util.UUID;

public class UserMapperTest {
    @Test
    public void getUserList(){
        SqlSession  sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.getUserList();

        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();

    }

    @Test
    public void getUserByUsername(){
        SqlSession  sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserByUsername("15098144981");

        System.out.println(user);

        sqlSession.close();
    }

    @Test
    public void getUserById(){
        SqlSession  sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserById("1");

        System.out.println(user);

        sqlSession.close();
    }

    @Test void insertUser(){
        SqlSession  sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User(UUID.randomUUID().toString(),"111","大哥",10 ,15, UUID.randomUUID().toString());
        userMapper.insetUser(user);
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void updateUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.updateUser(new User("15098144981","123456","张三",1000,100,"2"));
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void addUserEnergy(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.addUserEnergy(new ParamsTwoInt(10,20));
        sqlSession.commit();
        sqlSession.close();
    }
}
