package asia.zyq.shijing.mapper;

import asia.zyq.shijing.pojo.Paper;
import asia.zyq.shijing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PaperMapperTest {

    @Test
    public void getPaperById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PaperMapper paperMapper = sqlSession.getMapper(PaperMapper.class);
        Paper paper = paperMapper.getPaperById("1");
        System.out.println(paper);
        sqlSession.close();
    }

    @Test
    public void getPaperListBySubsectionId(){
        SqlSession  sqlSession = MybatisUtils.getSqlSession();
        PaperMapper paperMapper = sqlSession.getMapper(PaperMapper.class);
        List<Paper> paperList = paperMapper.getPaperListBySubsectionId("1");
        for (Paper paper : paperList) {
            System.out.println(paper);
        }
        sqlSession.close();

    }

}
