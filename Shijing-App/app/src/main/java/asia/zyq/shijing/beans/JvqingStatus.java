package asia.zyq.shijing.beans;

import java.util.ArrayList;
import java.util.List;

public class JvqingStatus {
    public List<Question> questionList = null;
    public List<String> answerList = new ArrayList<String>();
    public Integer i = -1;
    public String getAnswerString(){
        String ans = "";

        for (String s : answerList) {
            ans += s;
        }
        System.out.println(ans);
        return ans;
    }


}
