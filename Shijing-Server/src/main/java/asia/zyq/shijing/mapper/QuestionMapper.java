package asia.zyq.shijing.mapper;

import asia.zyq.shijing.pojo.Question;

import java.util.List;

public interface QuestionMapper {
    public List<Question> getQuestionListByPaperId(String paperId);
    public Question getQuestionById(String id);
}
