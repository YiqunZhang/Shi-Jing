package asia.zyq.shijing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.StringTokenizer;

import asia.zyq.shijing.presenter.SignInPresenter;
import asia.zyq.shijing.utils.DataUtils;
import asia.zyq.shijing.view.SignInView;

public class SignInInActivity extends AppCompatActivity implements SignInView {

    EditText editText_tel;
    EditText editText_password;
    Button button_signIn;
    Button button_signUpActivity;

    SignInPresenter signInPresenter;
    Intent intent = null;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(SignInInActivity.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    intent = new Intent();
                    intent.setClass(SignInInActivity.this,SignUpActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 3:
                    button_signIn.setText("登录中");
                    button_signIn.setEnabled(false);
                    editText_password.setEnabled(false);
                    editText_tel.setEnabled(false);
                    button_signUpActivity.setEnabled(false);

                    break;
                case 4:
                    button_signIn.setText("登录");
                    button_signIn.setEnabled(true);
                    editText_password.setEnabled(true);
                    editText_tel.setEnabled(true);
                    button_signUpActivity.setEnabled(true);
                    break;
                case 5:
                    intent = new Intent();
                    intent.setClass(SignInInActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        editText_tel = findViewById(R.id.editText_tel);
        editText_password = findViewById(R.id.editText_password);
        button_signIn = findViewById(R.id.button_signIn);
        button_signUpActivity = findViewById(R.id.button_signUpActivity);

        signInPresenter = new SignInPresenter(this);
        DataUtils.getInstance().setSp(getSharedPreferences("settings.txt", Context.MODE_PRIVATE));


        String fileUsername =  DataUtils.getInstance().getStringFromFile("username");
        String filePassword =  DataUtils.getInstance().getStringFromFile("password");
        if (fileUsername.length() > 0){
            editText_tel.setText(fileUsername);
            editText_password.setText(filePassword);
            signInPresenter.signInAuto();
        }

    }


    public void button_signUpActivity_onClick(View view){
        Message msg = new Message();
        msg.what = 2;
        handler.sendMessage(msg);
    }

    public void button_signIn_onClick(View view){
        signInPresenter.signIn();
    }

    @Override
    public void toast(String text) {
       Message msg = new Message();
       msg.what = 1;
       msg.obj = text;
       handler.sendMessage(msg);
    }

    @Override
    public void statusSignInLoading() {
        Message msg = new Message();
        msg.what = 3;
        handler.sendMessage(msg);
    }

    @Override
    public void statusDefault() {
        Message msg = new Message();
        msg.what = 4;
        handler.sendMessage(msg);
    }

    @Override
    public String getUsername() {
        return editText_tel.getText().toString();
    }

    @Override
    public String getPassword() {
        return editText_password.getText().toString();
    }

    @Override
    public void gotoSignUpActivity() {
        Message msg = new Message();
        msg.what = 5;
        handler.sendMessage(msg);
    }


}