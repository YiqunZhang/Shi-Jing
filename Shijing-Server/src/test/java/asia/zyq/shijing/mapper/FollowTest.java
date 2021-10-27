package asia.zyq.shijing.mapper;

import asia.zyq.shijing.beans.UserRankInfo;
import asia.zyq.shijing.pojo.Follow;
import asia.zyq.shijing.pojo.User;
import asia.zyq.shijing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FollowTest {
    @Test
    public void getFollowListByUserId(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        List<Follow> followList = followMapper.getFollowListByUserId("1");



        sqlSession.close();
        System.out.println(followList);
    }
    @Test
    public void getFollowByFollow(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        Follow follow = followMapper.getFollowByFollow(new Follow("1","2"));

        sqlSession.close();
        System.out.println(follow);
    }
    @Test
    public void deleteFollow(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        followMapper.deleteFollow(new Follow("1","2"));

        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void insertFollow (){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        followMapper.insertFollow(new Follow("1","3"));


        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void getFollowUserRankInfoListByUserId(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        List<UserRankInfo> userRankInfoList = followMapper.getFollowUserRankInfoListByUserId("123");



        sqlSession.close();
        for (UserRankInfo userRankInfo : userRankInfoList) {
            System.out.println(userRankInfo);
        }

    }
}
