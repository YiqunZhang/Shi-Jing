package asia.zyq.shijing.presenter;

import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asia.zyq.shijing.beans.Chapter;
import asia.zyq.shijing.beans.ListBean;
import asia.zyq.shijing.beans.PersonPaper;
import asia.zyq.shijing.beans.Subsection;
import asia.zyq.shijing.beans.UserRankInfo;
import asia.zyq.shijing.utils.Callback;
import asia.zyq.shijing.utils.DataUtils;
import asia.zyq.shijing.utils.ListType;
import asia.zyq.shijing.utils.NetParams;
import asia.zyq.shijing.utils.NetParamsGetter;
import asia.zyq.shijing.view.FollowView;
import asia.zyq.shijing.view.MyListView;

public class FollowPresenter implements NetParamsGetter {

    FollowView view;
    List<UserRankInfo> userRankInfoList = new ArrayList<UserRankInfo>();

    public FollowPresenter(FollowView view){
        this.view = view;

    }

    public void refreshItemList(){
        DataUtils.getInstance().get(getNetParams(1,null));
    }

    public void addFollow(String usernameFollow){

        DataUtils.getInstance().get(getNetParams(2,usernameFollow));

    }
    public UserRankInfo getUserRankInfo(Integer position){
        return userRankInfoList.get(position);
    }


    public void subFollow(String usernameFollow){
        DataUtils.getInstance().get(getNetParams(3,usernameFollow));
    }

    public String getText(Integer position){
        UserRankInfo userRankInfo = userRankInfoList.get(position);
        return userRankInfo.getNickname()+ ": 星星 " + userRankInfo.getStar();
    }

    public String getUserId(Integer position){
        UserRankInfo userRankInfo = userRankInfoList.get(position);
        return userRankInfo.getUserId();
    }

    public Integer getCount(){
        return userRankInfoList.size();
    }

    public NetParams getNetParams(Integer what, Object object) {
        if(what == 1){
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",DataUtils.getInstance().userInfo.getUsername());
            map.put("password",DataUtils.getInstance().userInfo.getPassword());
            return new NetParams("getFollowList", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("[")){
                        userRankInfoList = new Gson().fromJson(result, new TypeToken<List<UserRankInfo>>(){}.getType());
                        view.LoadSuccess();
                    }else {
                        view.toast("关注列表结果错误");
                        view.back();
                    }
                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("获取关注列表时发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                    view.back();
                }
            });
        } if(what == 2){
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",DataUtils.getInstance().userInfo.getUsername());
            map.put("password",DataUtils.getInstance().userInfo.getPassword());
            map.put("usernameFollow",(String)object);
            return new NetParams("addFollow", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("[")){
                        userRankInfoList = new Gson().fromJson(result, new TypeToken<List<UserRankInfo>>(){}.getType());
                        view.LoadSuccess();
                    }else {
                        view.toast(result);
                    }
                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("更新关注列表时发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                }
            });
        }if(what == 3){
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",DataUtils.getInstance().userInfo.getUsername());
            map.put("password",DataUtils.getInstance().userInfo.getPassword());
            map.put("usernameFollow",(String)object);
            return new NetParams("subFollow", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("[")){
                        userRankInfoList = new Gson().fromJson(result, new TypeToken<List<UserRankInfo>>(){}.getType());
                        view.LoadSuccess();
                    }else {
                        view.toast(result);
                    }
                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("更新关注列表时发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                }
            });
        }else  {
            return null;
        }
    }
}
