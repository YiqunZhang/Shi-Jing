package asia.zyq.shijing.mapper;

import asia.zyq.shijing.pojo.Question;
import asia.zyq.shijing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class QuestionMapperTest {
    @Test
    public void getQuestionListByPaperId(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
        List<Question> questionList = questionMapper.getQuestionListByPaperId("1");
        for (Question question : questionList) {
            System.out.println(question);
        }
        sqlSession.close();

    }
}
