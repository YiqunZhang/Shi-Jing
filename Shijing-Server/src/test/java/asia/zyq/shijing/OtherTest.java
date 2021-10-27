package asia.zyq.shijing;

import asia.zyq.shijing.Utils.MainUtilsTest;
import asia.zyq.shijing.beans.ParamsTwoString;
import asia.zyq.shijing.mapper.PersonPaperMapper;
import asia.zyq.shijing.pojo.PersonPaper;
import asia.zyq.shijing.utils.MainUtils;
import asia.zyq.shijing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.util.List;

public class OtherTest {
    @Test
    public void test1(){
        String res;
        res = MainUtils.getInstance().getPersonPaperListByUserIdSubsectionIdSorted(
                "15662332653",
                "1",
                "0a028f2b-a362-4979-a323-f11b98314a6f"
        ).toString();
        System.out.println(res);



    }

    @Test
    public void test2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonPaperMapper personPaperMapper = sqlSession.getMapper(PersonPaperMapper.class);
        List<PersonPaper> personPaperList = personPaperMapper.getPersonPaperListByUserIdSubsectionId(
                new ParamsTwoString(MainUtils.getInstance().getUserByUsername("15098144981").getId(),
                        "0a028f2b-a362-4979-a323-f11b98314a6f"));
        sqlSession.close();
        System.out.println(personPaperList);
        for (PersonPaper personPaper : personPaperList) {
            System.out.println(personPaper.getName());

        }
        System.out.println(personPaperList.size());
    }
}
