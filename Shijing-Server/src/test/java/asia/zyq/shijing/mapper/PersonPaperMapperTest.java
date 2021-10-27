package asia.zyq.shijing.mapper;

import asia.zyq.shijing.beans.ParamsTwoString;
import asia.zyq.shijing.pojo.PersonPaper;
import asia.zyq.shijing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PersonPaperMapperTest {
    @Test
    public void getPersonPaperListByUserIdSubsectionId(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonPaperMapper personPaperMapper = sqlSession.getMapper(PersonPaperMapper.class);
        List<PersonPaper> personPaperList = personPaperMapper.getPersonPaperListByUserIdSubsectionId(new ParamsTwoString("2","1"));
        for (PersonPaper personPaper : personPaperList) {
            System.out.println(personPaper);
        }
        sqlSession.close();

    }
}
