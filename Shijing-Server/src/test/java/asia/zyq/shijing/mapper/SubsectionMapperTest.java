package asia.zyq.shijing.mapper;

import asia.zyq.shijing.pojo.Chapter;
import asia.zyq.shijing.pojo.Subsection;
import asia.zyq.shijing.pojo.User;
import asia.zyq.shijing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SubsectionMapperTest {

    @Test
    public void getSubsectionList(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SubsectionMapper subsectionMapper = sqlSession.getMapper(SubsectionMapper.class);
        List<Subsection> subsectionList = subsectionMapper.getSubsectionListByChapterId("1");

        for (Subsection subsection : subsectionList) {
            System.out.println(subsection);
        }
        sqlSession.close();
    }

    @Test
    public void getSubsectionById(){
        SqlSession  sqlSession = MybatisUtils.getSqlSession();
        SubsectionMapper subsectionMapper = sqlSession.getMapper(SubsectionMapper.class);
        Subsection subsection = subsectionMapper.getSubsectionById("0a028f2b-a362-4979-a323-f11b98314a6f");

        System.out.println(subsection);

        sqlSession.close();
    }

}
