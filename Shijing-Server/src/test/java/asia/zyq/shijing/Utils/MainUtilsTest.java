package asia.zyq.shijing.Utils;

import asia.zyq.shijing.pojo.Person;
import asia.zyq.shijing.pojo.Question;
import asia.zyq.shijing.utils.MainUtils;
import org.junit.jupiter.api.Test;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class MainUtilsTest {

    @Test
    public void signIn(){
        assert MainUtils.getInstance().signIn("not_exist_id","123").equals("用户名不存在");
        assert MainUtils.getInstance().signIn("15098144981","error_password").equals("密码错误");
        assert MainUtils.getInstance().signIn("15098144981","123456").equals("SUCCESS_成功");
    }

    @Test
    public void signUp(){
        String randomId = UUID.randomUUID().toString();
        assert MainUtils.getInstance()
                .signUp(randomId,"password","nickname","000000").equals("SUCCESS_注册成功");
        assert MainUtils.getInstance().signUp("15098144981","password","nickname","000000").equals("FAIL_用户已存在");
    }

    @Test
    public void getChapterListSorted(){
        assert MainUtils.getInstance().getChapterListSorted().size() > 0;
    }

    @Test
    public void getSubsectionListByChapterIdSorted(){
        assert MainUtils.getInstance().getSubsectionListByChapterIdSorted("1").size() > 0;
    }

    @Test
    public void getPaperListBySubsectionIdSorted(){
        assert MainUtils.getInstance().getPaperListBySubsectionIdSorted("1").size() > 0;
    }



    @Test
    public void getQuestionListByPaperIdSortedNoAnswer(){
        List<Question> res = MainUtils.getInstance().getQuestionListByPaperIdSortedNoAnswer("1");
        assert res.size() > 0;
        for (Question question : res) {
            assert question.getAnswer().equals("");
        }
    }

    @Test
    public void getSetting(){
        assert Integer.parseInt(MainUtils.getInstance().getSetting("default_money")) == 0;
        assert MainUtils.getInstance().getSetting("default_null") == null;
    }

    @Test
    public void hideQuestionAnswer(){
        Question question = new Question("1","标题","题干","单选题","A"
                ,"B","C","D","A","1",5,"");
        Question questionNoAnswer = MainUtils.getInstance().hideQuestionAnswer(question);
        assert questionNoAnswer.getAnswer().equals("");
        questionNoAnswer.setAnswer("A");
        assert questionNoAnswer.equals(question);
    }

    @Test
    public void hideQuestionListAnsert(){
        List<Question> questionList = new ArrayList<Question>();
        for(int i = 0; i<10; i++){
            Question question = new Question(i+"","标题","题干","单选题","A"
                    ,"B","C","D","A","1",5,"");
            questionList.add(question);
        }
        List<Question> questionNoAnswerList = MainUtils.getInstance().hideQuestionListAnsert(questionList);
        for(int i = 0; i<questionList.size(); i++){
            Question questionNoAnswer = questionNoAnswerList.get(i);
            Question question = questionList.get(i);
            assert questionNoAnswer.getAnswer().equals("");
            questionNoAnswer.setAnswer("A");
            assert questionNoAnswer.equals(question);

        }
    }

    @Test
    public void getQuestionListByPaperId(){
        assert MainUtils.getInstance().getQuestionListByPaperIdSorted("1").size() > 0;
    }

    @Test
    public void getPersonListByUserId(){
        assert MainUtils.getInstance().getPersonListByUserId("2").size() > 0;
    }

    @Test
    public void getPersonPaperListByUserIdSubsectionIdSorted(){
        assert MainUtils.getInstance().getPersonPaperListByUserIdSubsectionIdSorted("2","123456","1").size() > 0;
        assert MainUtils.getInstance().getPersonPaperListByUserIdSubsectionIdSorted("2","1","1") == null;
    }

    @Test
    public void applyVerificationCode(){
        assert MainUtils.getInstance().applyVerificationCode("15098144981") != null;
    }

    @Test
    public void checkVerificationCode(){
        assert MainUtils.getInstance().checkVerificationCode("15098144981","000000");
    }

    @Test
    public void getPaperById(){
        assert MainUtils.getInstance().getPaperById("1") != null;
    }

    @Test
    public void submitPaper(){
        assert MainUtils.getInstance().submitPaper("15098144981","1","100","2") == null;
        assert MainUtils.getInstance().submitPaper("15098144981","123456","100","2") == null;
        assert MainUtils.getInstance().submitPaper("15098144981","123456","1","ABCCC") == null;
        assert MainUtils.getInstance().submitPaper("15098144981","123456","1","AXB") != null;
    }

    @Test
    public void insertPerson(){

    }

    @Test
    public void updatePerson(){

    }

    @Test
    public void getPersonByUserIdPaperId(){

    }

    @Test
    public void unlockPaper(){
        MainUtils.getInstance().unlockPaperNext("15098144981","100","");
        MainUtils.getInstance().unlockPaperNext("15098144981","101","");
        MainUtils.getInstance().unlockPaperNext("15098144981","102","");
    }

    @Test
    public void getRandomInteger(){

        for (int i = 0 ; i < 100 ; i++){
            System.out.print(MainUtils.getInstance().getRandomInteger(2) + "  ");
        }

    }

    @Test
    public void getCodeByTimeUsername(){
        System.out.println(MainUtils.getInstance().getCodeByTimeUsername("15098144981",0));
        System.out.println(MainUtils.getInstance().getCodeByTimeUsername("15098144981",1));
        System.out.println(MainUtils.getInstance().getCodeByTimeUsername("15098144981",0));
        System.out.println(MainUtils.getInstance().getCodeByTimeUsername("15098144981",1));

    }

    @Test
    public void temp(){

        MainUtils.getInstance().submitPaper("15098144981","123456","1","AVV");
    }




}
