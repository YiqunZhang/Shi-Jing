package asia.zyq.shijing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import asia.zyq.shijing.beans.UserInfo;
import asia.zyq.shijing.presenter.MainPresenter;
import asia.zyq.shijing.utils.DataUtils;
import asia.zyq.shijing.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    TextView textView;
    Intent intent = null;

    Button button_signOut;
    ImageButton button_rank;
    ImageButton button_jvqing;
    ImageButton button_jinnang;
    ImageButton button_follow;
    ImageButton button_achievement;

    ImageButton imageButton_notice;
    ImageView imageView_black;
    ImageView imageView_notice;
    ImageButton imageButton_close;
    TextView textView_notice;


    TextView textView_money;
    TextView textView_energy;

    MainPresenter mainPresenter;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    intent = new Intent();
                    intent.setClass(MainActivity.this, ListActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    break;
                case 3:
                    button_rank.setEnabled(false);
                    button_achievement.setEnabled(false);
                    button_follow.setEnabled(false);
                    button_jinnang.setEnabled(false);
                    button_jvqing.setEnabled(false);
                    button_signOut.setEnabled(false);
                    break;
                case 4:
                    button_rank.setEnabled(true);
                    button_achievement.setEnabled(true);
                    button_follow.setEnabled(true);
                    button_jinnang.setEnabled(true);
                    button_jvqing.setEnabled(true);
                    button_signOut.setEnabled(true);
                    break;
                case 5:
                    UserInfo tempUserInfo = (UserInfo) msg.obj;
                    textView_money.setText(tempUserInfo.getMoney() + "");
                    textView_energy.setText(tempUserInfo.getEnergy() + "/20");
                    textView.setText(tempUserInfo.toString());
                    break;
                case 6:
                    intent = new Intent();
                    intent.setClass(MainActivity.this, QuestionActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 7:
                    intent = new Intent();
                    intent.setClass(MainActivity.this, RankActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 8:
                    intent = new Intent();
                    intent.setClass(MainActivity.this, AchievementActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 9:
                    intent = new Intent();

                    intent.setClass(MainActivity.this, FollowActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    break;
                case 10:
                    //打开公告模式
                    imageView_black.setVisibility(View.VISIBLE);
                    imageView_notice.setVisibility(View.VISIBLE);
                    imageButton_close.setVisibility(View.VISIBLE);
                    textView_notice.setVisibility(View.VISIBLE);

                    imageView_notice.setEnabled(false);
                    button_signOut.setEnabled(false);
                    button_rank.setEnabled(false);
                    button_jvqing.setEnabled(false);
                    button_jinnang.setEnabled(false);
                    button_achievement.setEnabled(false);

                    textView_notice.setText(DataUtils.getInstance().userInfo.getNotice());
                    break;
                case 11:
                    //关闭公告模式
                    imageView_black.setVisibility(View.INVISIBLE);
                    imageView_notice.setVisibility(View.INVISIBLE);
                    imageButton_close.setVisibility(View.INVISIBLE);
                    textView_notice.setVisibility(View.INVISIBLE);

                    imageView_notice.setEnabled(true);
                    button_signOut.setEnabled(true);
                    button_rank.setEnabled(true);
                    button_jvqing.setEnabled(true);
                    button_jinnang.setEnabled(true);
                    button_achievement.setEnabled(true);


                    break;
                case 12:
                    if (DataUtils.getInstance().userInfo.getNotice() != null && DataUtils.getInstance().userInfo.getNotice().length() > 0){
                        imageButton_notice.setVisibility(View.VISIBLE);
                    }else{
                        imageButton_notice.setVisibility(View.INVISIBLE);
                    }
                    break;

            }
            return false;
        }
    });

    public void button_jinnang_onClick(View view) {
        mainPresenter.jinang();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.refreshUserInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_money = findViewById(R.id.textView_money);
        textView_energy = findViewById(R.id.textView_energy);

        textView = findViewById(R.id.textView_userInfo);
        button_achievement = findViewById(R.id.button_achievement);
        button_follow = findViewById(R.id.button_myfollow);
        button_jinnang = findViewById(R.id.button_jinnang);
        button_rank = findViewById(R.id.button_rank);
        button_jvqing = findViewById(R.id.button_jvqing);
        button_signOut = findViewById(R.id.button_signOut);

        imageButton_notice = findViewById(R.id.imageButton_notice);
        imageView_black = findViewById(R.id.imageView_black);
        imageView_notice = findViewById(R.id.imageView_notice);
        imageButton_close = findViewById(R.id.imageButton_close);
        textView_notice = findViewById(R.id.textView_notice);

        mainPresenter = new MainPresenter(this);

    }

    public void imageButton_close_onClick(View view){
        Message msg = new Message();
        msg.what = 11;
        handler.sendMessage(msg);
    }

    public void noticeCheck(){
        Message msg = new Message();
        msg.what = 12;
        handler.sendMessage(msg);
    }

    public void imageButton_notice_onClick(View view){
        Message msg = new Message();
        msg.what = 10;
        handler.sendMessage(msg);
    }

    public void button_signOut_onClick(View view) {
        DataUtils.getInstance().putStringToFile("username", "");
        DataUtils.getInstance().putStringToFile("password", "");
        DataUtils.getInstance().userInfo = null;
        intent = new Intent();
        intent.setClass(MainActivity.this, SignInInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void button_jvqing_onClick(View view) {
        mainPresenter.jvqing();
    }

    public void button_rank_onClick(View view) {
        Message msg = new Message();
        msg.what = 7;
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
    public void statusLoading() {
        Message msg = new Message();
        msg.what = 3;
        handler.sendMessage(msg);
    }

    public void button_myfollow_onClick(View view) {
        Message msg = new Message();
        msg.what = 9;
        handler.sendMessage(msg);
    }

    @Override
    public void statusDefault() {
        Message msg = new Message();
        msg.what = 4;
        handler.sendMessage(msg);
    }

    @Override
    public void gotoListActivity() {
        Message msg = new Message();
        msg.what = 2;
        handler.sendMessage(msg);
    }

    @Override
    public void gotoQuestionActivity() {
        Message msg = new Message();
        msg.what = 6;
        handler.sendMessage(msg);
    }

    public void button_achievement_onClick(View view) {

        Message msg = new Message();
        msg.what = 8;
        handler.sendMessage(msg);
    }

    @Override
    public void showUserInfo(UserInfo userInfo) {
        Message msg = new Message();
        msg.what = 5;
        msg.obj = userInfo;
        handler.sendMessage(msg);
    }
}