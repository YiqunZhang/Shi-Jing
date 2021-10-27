package asia.zyq.shijing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import asia.zyq.shijing.presenter.SignInPresenter;
import asia.zyq.shijing.presenter.SignUpPresenter;
import asia.zyq.shijing.view.SignUpView;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    Intent intent = null;
    EditText editText_tel_signUp = null;
    EditText editText_password_signUp = null;
    EditText editText_password_signUp_repeat = null;
    EditText editText_verification = null;
    EditText editText_nickName_signUp = null;
    Button button_signUp = null;
    Button button_getVerification = null;

    SignUpPresenter signUpPresenter;


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(SignUpActivity.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    intent = new Intent();
                    intent.setClass(SignUpActivity.this, SignInInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 3:
                    button_getVerification.setText("获取中");
                    button_getVerification.setEnabled(false);

                    break;
                case 4:
                    editText_password_signUp.setEnabled(false);
                    editText_password_signUp_repeat.setEnabled(false);
                    editText_tel_signUp.setEnabled(false);
                    editText_verification.setEnabled(false);
                    button_signUp.setEnabled(false);
                    button_getVerification.setEnabled(false);
                    button_signUp.setText("注册中");
                    break;
                case 5:
                    editText_password_signUp.setEnabled(true);
                    editText_password_signUp_repeat.setEnabled(true);
                    editText_tel_signUp.setEnabled(true);
                    editText_verification.setEnabled(true);
                    button_signUp.setEnabled(true);
                    button_signUp.setText("注册");
                    if (button_getVerification.getText().equals("获取")){
                        button_getVerification.setEnabled(true);
                    }

                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editText_tel_signUp = findViewById(R.id.editText_tel_signUp);
        editText_password_signUp = findViewById(R.id.editText_password_signUp);
        editText_password_signUp_repeat = findViewById(R.id.editText_password_signUp_repeat);
        editText_verification = findViewById(R.id.editText_verification);
        editText_nickName_signUp = findViewById(R.id.editText_nickName_signUp);
        button_signUp = findViewById(R.id.button_signUp);
        button_getVerification = findViewById(R.id.button_getVerification);

        signUpPresenter = new SignUpPresenter(this);

    }

    public void button_signUp_onClick(View view){
        signUpPresenter.signUp();
    }

    public void button_getVerification_onClick(View view){
        signUpPresenter.getVerificationCode();
    }

    @Override
    public void statusSignUpLoading() {
        Message msg = new Message();
        msg.what = 4;
        handler.sendMessage(msg);
    }

    @Override
    public void statusDefault() {
        Message msg = new Message();
        msg.what = 5;
        handler.sendMessage(msg);
    }

    @Override
    public void toast(String text) {
        Message msg = new Message();
        msg.what = 1;
        msg.obj = text;
        handler.sendMessage(msg);
    }

    @Override
    public void statusVerificationWait() {
        Message msg = new Message();
        msg.what = 3;
        handler.sendMessage(msg);
    }

    @Override
    public void gotoSignInActivity() {
        Message msg = new Message();
        msg.what = 2;
        handler.sendMessage(msg);
    }

    @Override
    public String getUsername() {
        return editText_tel_signUp.getText().toString();
    }

    @Override
    public String getNickname() {
        return editText_nickName_signUp.getText().toString();
    }

    @Override
    public String getPassword() {
        return editText_password_signUp.getText().toString();
    }

    @Override
    public String getPasswordRepeat() {
        return editText_password_signUp_repeat.getText().toString();
    }

    @Override
    public String getVerification() {
        return editText_verification.getText().toString();
    }
}
