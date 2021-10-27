package asia.zyq.shijing.presenter;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import asia.zyq.shijing.beans.UserInfo;
import asia.zyq.shijing.utils.Callback;
import asia.zyq.shijing.utils.DataUtils;
import asia.zyq.shijing.utils.NetParams;
import asia.zyq.shijing.utils.NetParamsGetter;
import asia.zyq.shijing.view.SignInView;
import asia.zyq.shijing.view.SignUpView;

public class SignUpPresenter implements NetParamsGetter {
    SignUpView view;
    public SignUpPresenter(SignUpView view){
        this.view = view;
    }

    public void signUp(){
        view.statusSignUpLoading();
        try {
            String username = view.getUsername();
            String password = view.getPassword();
            if (username.isEmpty()){
                view.toast("手机不能为空");
                view.statusDefault();
            }else if (password.isEmpty()){
                view.toast("密码不能为空");
                view.statusDefault();
            }else if(view.getNickname().equals("")){
                view.toast("昵称不能为空");
                view.statusDefault();
            }else if(view.getVerification().equals("")){
                view.toast("验证码不能为空");
                view.statusDefault();
            }else if (!password.equals(view.getPasswordRepeat())){
                 view.toast("两次输入的密码不一致");
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

    public void getVerificationCode(){
        view.statusVerificationWait();
        DataUtils.getInstance().get(getNetParams(2,null));
    }

    @Override
    public NetParams getNetParams(Integer what,Object object) {
        if (what == 1){
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",view.getUsername());
            map.put("password",view.getPassword());
            map.put("nickname",view.getNickname());
            map.put("verificationCode",view.getVerification());

            return new NetParams("signUp", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.equals("SUCCESS")){
                        view.toast("注册成功");
                        view.statusDefault();
                        view.gotoSignInActivity();
                    }else {
                        view.toast(result);
                        view.statusDefault();
                    }

                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("注册过程发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                    view.statusDefault();
                }
            });
        }else {
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",view.getUsername());
            return new NetParams("applyVerificationCode", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.equals("SUCCESS")){
                        view.toast("获取成功");
                    }else {
                        view.toast("获取失败");
                    }
                    view.statusDefault();
                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("获取验证码过程发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                    view.statusDefault();
                }
            });
        }

    }
}
