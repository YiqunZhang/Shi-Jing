package asia.zyq.shijing.mapper;

import asia.zyq.shijing.pojo.Chapter;
import asia.zyq.shijing.pojo.Subsection;
import asia.zyq.shijing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ChapterMapperTest {

    @Test
    public void getChapterList(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ChapterMapper chapterMapper = sqlSession.getMapper(ChapterMapper.class);
        List<Chapter> chapterList = chapterMapper.getChapterList();

        for (Chapter chapter : chapterList) {
            System.out.println(chapter);
        }
        sqlSession.close();
    }

    @Test
    public void getSubsectionById(){
        SqlSession  sqlSession = MybatisUtils.getSqlSession();
        ChapterMapper chapterMapper = sqlSession.getMapper(ChapterMapper.class);
        Chapter chapter = chapterMapper.getChapterById("4ea9e918-4b67-45c8-84f2-f735d01f33d2");

        System.out.println(chapter);

        sqlSession.close();
    }
}
