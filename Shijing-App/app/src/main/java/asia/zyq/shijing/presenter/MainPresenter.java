package asia.zyq.shijing.presenter;

import android.os.Message;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import asia.zyq.shijing.beans.PersonPaper;
import asia.zyq.shijing.beans.UserInfo;
import asia.zyq.shijing.utils.Callback;
import asia.zyq.shijing.utils.DataUtils;
import asia.zyq.shijing.utils.ListType;
import asia.zyq.shijing.utils.NetParams;
import asia.zyq.shijing.utils.NetParamsGetter;
import asia.zyq.shijing.utils.QuestionType;
import asia.zyq.shijing.view.MainView;

public class MainPresenter implements NetParamsGetter {
    MainView view;
    public MainPresenter(MainView view){
        this.view = view;
    }

    public void jvqing(){
        view.statusLoading();
        try {
            DataUtils.getInstance().listTypeSender = ListType.ROOT;
            DataUtils.getInstance().itemIdSender = "";
            view.statusDefault();
            view.gotoListActivity();
        }catch (Exception e){
            e.printStackTrace();
            view.statusDefault();
        }
    }

    public void jinang() {
        DataUtils.getInstance().questionType = QuestionType.JINNANG;
        DataUtils.getInstance().paperIdSender = "00000000-0000-0000-0000-000000000000";

        view.gotoQuestionActivity();
    }

    public void refreshUserInfo(){
        DataUtils.getInstance().get(getNetParams(1,null));
    }

    public NetParams getNetParams(Integer what, Object object) {
        if (what == 1){
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",DataUtils.getInstance().userInfo.getUsername());
            map.put("password",DataUtils.getInstance().userInfo.getPassword());
            return new NetParams("signIn", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("{")){
                        DataUtils.getInstance().userInfo = (UserInfo) new Gson().fromJson(result, UserInfo.class);
                        view.showUserInfo(DataUtils.getInstance().userInfo);
                        view.noticeCheck();
                    }else {
                        view.toast("获取个人信息失败");
                    }

                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("获取个人信息过程发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                    view.statusDefault();
                }
            });
        }else {
            return null;
        }

    }
}
