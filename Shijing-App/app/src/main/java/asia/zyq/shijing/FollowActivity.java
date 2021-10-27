package asia.zyq.shijing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import asia.zyq.shijing.beans.UserRankInfo;
import asia.zyq.shijing.presenter.FollowPresenter;
import asia.zyq.shijing.view.FollowView;

public class FollowActivity extends AppCompatActivity implements FollowView, AdapterView.OnItemClickListener {

    FollowPresenter followPresenter = null;
    ListView listView = null;
    EditText editText_follow = null;
    Intent intent;
    BaseAdapter baseAdapter;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    baseAdapter = new ThisListAdapter();
                    listView.setAdapter(baseAdapter);
                    listView.setOnItemClickListener(FollowActivity.this);
                    break;
                case 1:
                    Toast.makeText(FollowActivity.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    finish();
                    break;

                case 3:
                    baseAdapter.notifyDataSetChanged();
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        listView = findViewById(R.id.listView_follow);
        editText_follow = findViewById(R.id.editText_follow);
        followPresenter = new FollowPresenter(this);
        followPresenter.refreshItemList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        showNotFollowDialog(followPresenter.getUserRankInfo(position));
    }

    @Override
    public void toast(String text) {
        Message msg = new Message();
        msg.what = 1;
        msg.obj = text;
        handler.sendMessage(msg);
    }

    @Override
    public void back() {
        Message msg = new Message();
        msg.what = 2;
        handler.sendMessage(msg);
    }

    public void button_followUser_onClick(View view){


        String usernameFollow = editText_follow.getText().toString();
        if (usernameFollow == ""){
            toast("请输入被关注者手机号");
        }else {
            followPresenter.addFollow(usernameFollow);
        }

    }

    @Override
    public void LoadSuccess() {
        editText_follow.setText("");
        if (baseAdapter == null){
            Message msg = new Message();
            msg.what = 0;
            handler.sendMessage(msg);
        }else {
            Message msg = new Message();
            msg.what = 3;
            handler.sendMessage(msg);
        }

    }

    private void showNotFollowDialog(UserRankInfo userRankInfo){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(FollowActivity.this);
        //normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("取关");

        normalDialog.setMessage("你确定要取关 "+ userRankInfo.getNickname() + "(手机" + userRankInfo.getUsername() +") 吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        followPresenter.subFollow(userRankInfo.getUsername());
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示

        normalDialog.show();


    }

    private class ThisListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return followPresenter.getCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = View.inflate(FollowActivity.this,R.layout.activity_follow_item,null);
            TextView textView_follow = v.findViewById(R.id.textView_follow);
            textView_follow.setText(followPresenter.getText(position));

            ImageView imageView_follow_item = v.findViewById(R.id.imageView_follow_item);
            switch (position % 3){
                case 0:
                    imageView_follow_item.setImageResource(R.drawable.ui_item_follow_1);
                    break;
                case 1:
                    imageView_follow_item.setImageResource(R.drawable.ui_item_follow_2);
                    break;
                case 2:
                    imageView_follow_item.setImageResource(R.drawable.ui_item_follow_3);
                    break;
            }

            TextView textView_followid = v.findViewById(R.id.textView_followid);
            textView_followid.setText(followPresenter.getUserId(position));
            return v;
        }
    }
}