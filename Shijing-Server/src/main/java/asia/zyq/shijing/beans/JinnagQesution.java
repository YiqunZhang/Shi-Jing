package asia.zyq.shijing.beans;

import asia.zyq.shijing.pojo.Question;
import lombok.Data;

import java.util.Objects;

@Data
public class JinnagQesution {
    private String id;
    private String title;
    private String text;
    private String type;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String paperid;
    private Integer number;
    private String analysis;
    private Integer totalMoney;
    private Integer addMoney;
    private Boolean correct;

    public JinnagQesution(Question question, Integer totalMoney, Integer addMoney, Boolean correct) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.text = question.getText();
        this.type = question.getType();
        this.optionA = question.getOptionA();
        this.optionB = question.getOptionB();
        this.optionC = question.getOptionC();
        this.optionD = question.getOptionD();
        this.answer = question.getAnswer();
        this.paperid = question.getPaperid();
        this.number = question.getNumber();
        this.analysis = question.getAnalysis();
        this.totalMoney = totalMoney;
        this.addMoney = addMoney;
        this.correct = correct;
    }




}
