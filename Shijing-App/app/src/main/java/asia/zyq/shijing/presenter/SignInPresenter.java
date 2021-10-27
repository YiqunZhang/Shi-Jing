package asia.zyq.shijing.presenter;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import asia.zyq.shijing.beans.UserInfo;
import asia.zyq.shijing.utils.Callback;
import asia.zyq.shijing.utils.NetParams;
import asia.zyq.shijing.utils.NetParamsGetter;
import asia.zyq.shijing.utils.DataUtils;
import asia.zyq.shijing.view.SignInView;

public class SignInPresenter implements NetParamsGetter {
    SignInView view;
    public SignInPresenter(SignInView view){
        this.view = view;
    }



    public void signIn(){
        view.statusSignInLoading();
        try {
            String username = view.getUsername();
            String password = view.getPassword();
            if (username.isEmpty()){
                view.toast("手机不能为空");
                view.statusDefault();
            }else if (password.isEmpty()){
                view.toast("密码不能为空");
                view.statusDefault();
            }else {
                DataUtils.getInstance().get(getNetParams(1,null));
            }
        }catch (Exception e){
            view.toast("登录过程发生未知错误");
            view.statusDefault();
        }finally {

        }
    }

    public void signInAuto(){
        view.statusSignInLoading();
        try {
            String username = view.getUsername();
            String password = view.getPassword();
            if (username.isEmpty()){
                view.toast("手机不能为空");
                view.statusDefault();
            }else if (password.isEmpty()){
                view.toast("密码不能为空");
                view.statusDefault();
            }else {
                DataUtils.getInstance().get(getNetParams(2,null));
            }
        }catch (Exception e){
            view.toast("自动登录失败");
            view.statusDefault();
        }finally {

        }
    }

    @Override
    public NetParams getNetParams(Integer what, Object object) {
        if (what == 1){
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",view.getUsername());
            map.put("password",view.getPassword());

            return new NetParams("signIn", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("{")){
                        DataUtils.getInstance().userInfo = (UserInfo) new Gson().fromJson(result, UserInfo.class);

                        DataUtils.getInstance().putStringToFile("username",view.getUsername());
                        DataUtils.getInstance().putStringToFile("password",view.getPassword());

                        view.statusDefault();
                        view.gotoSignUpActivity();
                    }else {
                        view.toast(result);
                        view.statusDefault();
                    }

                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("登录过程发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                    view.statusDefault();
                }
            });
        }else if(what == 2){
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",view.getUsername());
            map.put("password",view.getPassword());

            return new NetParams("signIn", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("{")){
                        DataUtils.getInstance().userInfo = (UserInfo) new Gson().fromJson(result, UserInfo.class);
                        //view.toast("登录成功");
                        view.statusDefault();
                        view.gotoSignUpActivity();
                    }else {

                        view.toast("自动登录失败,"+result);
                        DataUtils.getInstance().putStringToFile("username","");
                        DataUtils.getInstance().putStringToFile("password","");
                        view.statusDefault();
                    }

                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("自动登录过程发生未知错误");
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
