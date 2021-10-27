package asia.zyq.shijing.presenter;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asia.zyq.shijing.beans.UserInfo;
import asia.zyq.shijing.beans.UserRankInfo;
import asia.zyq.shijing.utils.Callback;
import asia.zyq.shijing.utils.DataUtils;
import asia.zyq.shijing.utils.NetParams;
import asia.zyq.shijing.utils.NetParamsGetter;
import asia.zyq.shijing.view.RankView;

public class RankPresenter implements NetParamsGetter {
    RankView view;
    List<UserRankInfo> userRankInfoList = new ArrayList<UserRankInfo>();


    public RankPresenter(RankView view){
        this.view = view;
    }
    public Integer getCount(){
        return userRankInfoList.size();
    }

    public String getNickName(Integer position){
        return userRankInfoList.get(position).getNickname();
    }

    public String getUserId(Integer position){
        return userRankInfoList.get(position).getUserId();
    }
    public Integer getStar(Integer position){
        return userRankInfoList.get(position).getStar();
    }

    public void loadRank(){
        DataUtils.getInstance().get(getNetParams(1,null));
    }

    @Override
    public NetParams getNetParams(Integer what, Object object) {
        if(what == 1){
            Map<String,String> map = new HashMap<String,String>();
            return new NetParams("getRankList", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("[")){
                        userRankInfoList = new Gson().fromJson(result, new TypeToken<List<UserRankInfo>>(){}.getType());

                        view.LoadSuccess();
                    }else {
                        view.toast("获取排名值不正确");
                        view.back();

                    }

                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("获取排名时发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                    view.back();
                }
            });
        }else  {
            return null;
        }
    }
}
