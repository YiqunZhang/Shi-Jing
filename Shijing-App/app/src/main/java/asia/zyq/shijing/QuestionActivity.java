package asia.zyq.shijing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import asia.zyq.shijing.beans.PaperSubmitReport;
import asia.zyq.shijing.beans.Question;
import asia.zyq.shijing.presenter.QuestionPresenter;
import asia.zyq.shijing.view.QuestionView;

public class QuestionActivity extends AppCompatActivity implements QuestionView {

    TextView textView_title = null;
    TextView textView_text = null;
    TextView textView_mode = null;
    ImageButton button_A = null;
    ImageButton button_B = null;
    ImageButton button_C = null;
    ImageButton button_D = null;
    TextView textView_A = null;
    TextView textView_B = null;
    TextView textView_C = null;
    TextView textView_D = null;

    TextView textView_finish = null;
    ImageView imageView_finish_0 = null;
    ImageView imageView_finish_1 = null;
    ImageView imageView_finish_2 = null;
    ImageView imageView_finish_3 = null;
    ImageView imageView_finish_4 = null;
    ImageView imageView_finish_5 = null;


    TextView textView_finish_moneys = null;
    TextView textView_finish_energys = null;
    TextView textView_finish_star = null;
    TextView textView_finish_energy = null;
    TextView textView_finish_money = null;


    ImageButton button_nextQuestion = null;
    ConstraintLayout constraintLayout = null;

    Boolean done = false;

    QuestionPresenter questionPresenter = null;


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(QuestionActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    textView_title.setText(((Question) msg.obj).getTitle());
                    textView_mode.setText("单选");


                    //button_nextQuestion.setText("跳过次题");
                    if (((Question) msg.obj).getOptionA().length() > 0) {
                        button_A.setVisibility(View.VISIBLE);
                        textView_A.setVisibility(View.VISIBLE);
                        textView_A.setText("A. " + ((Question) msg.obj).getOptionA());
                    } else {
                        button_A.setVisibility(View.INVISIBLE);
                        textView_A.setVisibility(View.INVISIBLE);
                    }

                    if (((Question) msg.obj).getOptionB().length() > 0) {
                        button_B.setVisibility(View.VISIBLE);
                        textView_B.setVisibility(View.VISIBLE);
                        textView_B.setText("B. " + ((Question) msg.obj).getOptionB());
                    } else {
                        button_B.setVisibility(View.INVISIBLE);
                        textView_B.setVisibility(View.INVISIBLE);
                    }

                    if (((Question) msg.obj).getOptionC().length() > 0) {
                        button_C.setVisibility(View.VISIBLE);
                        textView_C.setVisibility(View.VISIBLE);
                        textView_C.setText("C. " + ((Question) msg.obj).getOptionC());
                    } else {
                        button_C.setVisibility(View.INVISIBLE);
                        textView_C.setVisibility(View.INVISIBLE);
                    }
                    if (((Question) msg.obj).getOptionD().length() > 0) {
                        button_D.setVisibility(View.VISIBLE);
                        textView_D.setVisibility(View.VISIBLE);
                        textView_D.setText("D. " + ((Question) msg.obj).getOptionD());
                    } else {
                        button_D.setVisibility(View.INVISIBLE);
                        textView_D.setVisibility(View.INVISIBLE);
                    }

                    textView_text.setText(((Question) msg.obj).getText());

                    break;
                case 3:
                    textView_title.setText(((Question) msg.obj).getTitle());
                    textView_mode.setText("剧情");

                    //button_nextQuestion.setText(">>>");
                    button_A.setVisibility(View.INVISIBLE);
                    textView_A.setVisibility(View.INVISIBLE);
                    button_B.setVisibility(View.INVISIBLE);
                    textView_B.setVisibility(View.INVISIBLE);
                    button_C.setVisibility(View.INVISIBLE);
                    textView_C.setVisibility(View.INVISIBLE);
                    button_D.setVisibility(View.INVISIBLE);
                    textView_D.setVisibility(View.INVISIBLE);
                    textView_text.setText(((Question) msg.obj).getText());


                    break;
                case 4:
                    textView_title.setText(((Question) msg.obj).getTitle());
                    textView_mode.setText("解析");
                    //button_nextQuestion.setText("继续");
                    button_A.setVisibility(View.INVISIBLE);
                    textView_A.setVisibility(View.INVISIBLE);
                    button_B.setVisibility(View.INVISIBLE);
                    textView_B.setVisibility(View.INVISIBLE);
                    button_C.setVisibility(View.INVISIBLE);
                    textView_C.setVisibility(View.INVISIBLE);
                    button_D.setVisibility(View.INVISIBLE);
                    textView_D.setVisibility(View.INVISIBLE);
                    String analysisText = "答案: " + ((Question) msg.obj).getAnswer() + "\n\n解析:\n" + ((Question) msg.obj).getAnalysis();
                    textView_text.setText(analysisText);


                    break;
                case 5:
                    finish();
                    break;
                case 6:
                    PaperSubmitReport report = (PaperSubmitReport) msg.obj;
                    textView_title.setText("恭喜完成！");
                    constraintLayout.setBackgroundResource(R.drawable.ui_bg_question_finish);
                    //button_nextQuestion.setText("完成");
                    done = true;
                    button_A.setVisibility(View.INVISIBLE);
                    textView_A.setVisibility(View.INVISIBLE);
                    button_B.setVisibility(View.INVISIBLE);
                    textView_B.setVisibility(View.INVISIBLE);
                    button_C.setVisibility(View.INVISIBLE);
                    textView_C.setVisibility(View.INVISIBLE);
                    button_D.setVisibility(View.INVISIBLE);
                    textView_D.setVisibility(View.INVISIBLE);

                    textView_finish_moneys.setVisibility(View.VISIBLE);
                    textView_finish_energys.setVisibility(View.VISIBLE);
                    textView_finish_energy.setVisibility(View.VISIBLE);
                    textView_finish_money.setVisibility(View.VISIBLE);
                    textView_finish_star.setVisibility(View.VISIBLE);
                    textView_finish_energy.setText("-" + (report.getTotalNumber()-report.getCorrectNumber()));
                    textView_finish_star.setText("" + report.getStar());
                    textView_finish_money.setText("+" + report.getMoney());
                    textView_finish_moneys.setText(report.getTotalMoney() + "");
                    textView_finish_energys.setText(report.getTotalEnergy() + "");

                    button_nextQuestion.setBackgroundResource(R.drawable.ui_button_next_red);

                    imageView_finish_0.setVisibility(View.VISIBLE);
                    imageView_finish_1.setVisibility(View.VISIBLE);
                    imageView_finish_2.setVisibility(View.VISIBLE);
                    imageView_finish_3.setVisibility(View.VISIBLE);
                    imageView_finish_4.setVisibility(View.VISIBLE);
                    imageView_finish_5.setVisibility(View.VISIBLE);

                    textView_finish.setVisibility(View.VISIBLE);
                    textView_text.setVisibility(View.INVISIBLE);
                    textView_mode.setText("结算");
                    textView_finish.setText("题目: " + report.getTotalNumber() + "\n正确: " + report.getCorrectNumber());


                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        textView_title = findViewById(R.id.textView_title);
        textView_mode = findViewById(R.id.textView_mode);
        textView_text = findViewById(R.id.textView_text);
        button_A = findViewById(R.id.button_A);
        button_B = findViewById(R.id.button_B);
        button_C = findViewById(R.id.button_C);
        button_D = findViewById(R.id.button_D);
        textView_A = findViewById(R.id.textView_A);
        textView_B = findViewById(R.id.textView_B);
        textView_C = findViewById(R.id.textView_C);
        textView_D = findViewById(R.id.textView_D);
        button_nextQuestion = findViewById(R.id.imageButton_nextQuestion);
        constraintLayout = findViewById(R.id.activity_question);

        textView_finish = findViewById(R.id.textView_finish_main);
        imageView_finish_0 = findViewById(R.id.imageView_finish_0);
        imageView_finish_1 = findViewById(R.id.imageView_finish_1);
        imageView_finish_2 = findViewById(R.id.imageView_finish_2);
        imageView_finish_3 = findViewById(R.id.imageView_finish_3);
        imageView_finish_4 = findViewById(R.id.imageView_finish_4);
        imageView_finish_5 = findViewById(R.id.imageView_finish_5);

        textView_finish_moneys = findViewById(R.id.textView_finish_moneys);
        textView_finish_energys = findViewById(R.id.textView_finish_energys);
        textView_finish_money = findViewById(R.id.textView_finish_money);
        textView_finish_star = findViewById(R.id.textView_finish_star);
        textView_finish_energy = findViewById(R.id.textView_finish_energy);

        questionPresenter = new QuestionPresenter(this);
    }


    public void button_nextQuestion_onClick(View view) {
        if (done) {
            Message msg = new Message();
            msg.what = 5;
            handler.sendMessage(msg);
        } else {
            questionPresenter.getNextQuestion();
        }

    }


    public void showMultiple(Question question) {
        Message msg = new Message();
        msg.what = 2;
        msg.obj = question;
        handler.sendMessage(msg);

    }

    public void showNoOptions(Question question) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = question;
        handler.sendMessage(msg);
    }

    public void showAnalysis(Question question) {
        Message msg = new Message();
        msg.what = 4;
        msg.obj = question;
        handler.sendMessage(msg);

    }

    public void button_A_onClick(View view) {
        questionPresenter.select("A");
    }

    public void button_B_onClick(View view) {
        questionPresenter.select("B");
    }

    public void button_C_onClick(View view) {
        questionPresenter.select("C");
    }

    public void button_D_onClick(View view) {
        questionPresenter.select("D");
    }

    @Override
    public void showJvqingSettlement(PaperSubmitReport paperSubmitReport) {
        Message msg = new Message();
        msg.what = 6;
        msg.obj = paperSubmitReport;
        handler.sendMessage(msg);
    }

    @Override
    public void back() {
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


}