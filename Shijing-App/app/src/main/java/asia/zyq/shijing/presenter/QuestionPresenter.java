package asia.zyq.shijing.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asia.zyq.shijing.beans.JinnagQesution;
import asia.zyq.shijing.beans.PaperSubmitReport;
import asia.zyq.shijing.beans.Question;
import asia.zyq.shijing.beans.JvqingStatus;
import asia.zyq.shijing.utils.Callback;
import asia.zyq.shijing.utils.DataUtils;
import asia.zyq.shijing.utils.NetParams;
import asia.zyq.shijing.utils.NetParamsGetter;
import asia.zyq.shijing.utils.QuestionType;
import asia.zyq.shijing.view.QuestionView;

public class QuestionPresenter implements NetParamsGetter {
    QuestionView view;
    QuestionType questionType = QuestionType.JVQING;
    String paperIdSender = "";

    Question jinnangNowQuestion = new Question("","","","","","","","","","",0,"");
    String jinangNowAnswer = "";
    JvqingStatus status = null;


    public QuestionPresenter(QuestionView view){
        this.view = view;
        questionType = DataUtils.getInstance().questionType;

        if (questionType == QuestionType.JVQING){
            status = new JvqingStatus();
            status.i = -1;
            this.paperIdSender = DataUtils.getInstance().paperIdSender;
            DataUtils.getInstance().get(getNetParams(1,null));
        }else if (questionType == QuestionType.JINNANG){
            DataUtils.getInstance().get(getNetParams(3,null));
        }

    }

    public void select(String answerSelected){
        if (questionType == QuestionType.JVQING){
            status.answerList.set(status.i,answerSelected);
            view.showAnalysis(status.questionList.get(status.i));
        }else if (questionType == QuestionType.JINNANG){
            jinangNowAnswer = answerSelected;
            view.showAnalysis(jinnangNowQuestion);

        }

    }


    public void getNextQuestion(){
        if (questionType == QuestionType.JVQING){
            status.i ++;
            if (status.i < status.questionList.size()){

                Question question = status.questionList.get(status.i);
                if (question.getType().equals("单选")){
                    view.showMultiple(question);
                }else {
                    view.showNoOptions(question);
                }
            }else {
                DataUtils.getInstance().get(getNetParams(2,null));
            }

        }else if (questionType == QuestionType.JINNANG){
            DataUtils.getInstance().get(getNetParams(4,null));
        }
    }

    @Override
    public NetParams getNetParams(Integer what, Object object) {
        if (what == 1){
            Map<String,String> map = new HashMap<String,String>();
            map.put("paperId",paperIdSender);
            return new NetParams("getQuestionListByPaperIdSorted", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("[")){
                        status.questionList = new Gson().fromJson(result, new TypeToken<List<Question>>(){}.getType());

                        for (int i = 0; i < status.questionList.size(); i++){
                            status.answerList.add("_");
                        }
                        getNextQuestion();
                    }else {
                        view.toast("发生错误");
                        view.back();
                    }
                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                    view.back();
                }
            });
        }else if (what == 2){
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",DataUtils.getInstance().userInfo.getUsername());
            map.put("password",DataUtils.getInstance().userInfo.getPassword());
            map.put("paperId",paperIdSender);
            map.put("answer",status.getAnswerString());
            return new NetParams("submitPaper", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("{")){
                        PaperSubmitReport paperSubmitReport = new Gson().fromJson(result, PaperSubmitReport.class);
                        DataUtils.getInstance().userInfo.setEnergy(paperSubmitReport.getTotalEnergy());
                        DataUtils.getInstance().userInfo.setMoney(paperSubmitReport.getTotalMoney());

                        view.showJvqingSettlement(paperSubmitReport);
                    }else {
                        view.toast("发生错误");
                        view.back();
                    }
                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                    view.back();
                }
            });
        }else if (what == 3 || what == 4){
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",DataUtils.getInstance().userInfo.getUsername());
            map.put("password",DataUtils.getInstance().userInfo.getPassword());
            map.put("quesitonId",jinnangNowQuestion.getId());
            map.put("answer",jinangNowAnswer);
            return new NetParams("submitGetJinnangQuestion", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("{")){
                        JinnagQesution jinnagQesution = new Gson().fromJson(result, JinnagQesution.class);
                        DataUtils.getInstance().userInfo.setMoney(jinnagQesution.getTotalMoney());
                        if (what == 3){
                            view.toast("加载成功");
                        }else if(what == 4){
                            String temp = "错误";
                            if (jinnagQesution.getCorrect()){
                                temp = "正确";
                            }
                            view.toast("上一题回答"+temp+",获得银两" + jinnagQesution.getAddMoney()+",总银两"+jinnagQesution.getTotalMoney());
                        }
                        jinnangNowQuestion = jinnagQesution.getQuestion();
                        view.showMultiple(jinnangNowQuestion);
                        //view.showJvqingSettlement(paperSubmitReport);
                    }else {
                        view.toast("发生错误");
                        view.back();
                    }
                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                    view.back();
                }
            });
        }else {
            return null;
        }

    }
}
