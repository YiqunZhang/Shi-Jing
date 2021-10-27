package asia.zyq.shijing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import asia.zyq.shijing.presenter.RankPresenter;
import asia.zyq.shijing.utils.DataUtils;
import asia.zyq.shijing.view.RankView;

public class RankActivity extends AppCompatActivity implements RankView, AdapterView.OnItemClickListener {

    RankPresenter rankPresenter = null;
    ListView listView= null;
    Intent intent;
    BaseAdapter baseAdapter;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    baseAdapter = new ThisListAdapter();
                    listView.setAdapter(baseAdapter);
                    listView.setOnItemClickListener(RankActivity.this);
                    break;
                case 1:
                    Toast.makeText(RankActivity.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    finish();
                    break;



            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        listView = findViewById(R.id.listView_rank);

        rankPresenter = new RankPresenter(this);
        rankPresenter.loadRank();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void LoadSuccess() {
        Message msg = new Message();
        msg.what = 0;
        handler.sendMessage(msg);
    }
    @Override
    public void back(){
        Message msg = new Message();
        msg.what = 2;
        handler.sendMessage(msg);
    }

    @Override
    public void toast(String text) {
        Message msg = new Message();
        msg.what = 1;
        msg.obj = text;
        handler.sendMessage(msg);
    }

    private class ThisListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return rankPresenter.getCount();
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
            View v = View.inflate(RankActivity.this,R.layout.activity_rank_item,null);
            TextView textView_rank = v.findViewById(R.id.textView_rank);
            TextView textView_rank_num = v.findViewById(R.id.textView_rank_num);
            ImageView imageView_medal = v.findViewById(R.id.imageView_medal);
            ImageView imageView_rank = v.findViewById(R.id.imageView_rank);
            TextView textView_rank_star = v.findViewById(R.id.textView_rank_star);
            textView_rank_star.setText(rankPresenter.getStar(position) + "");
            switch (position){
                case 0:
                    imageView_medal.setVisibility(View.VISIBLE);
                    textView_rank_num.setVisibility(View.INVISIBLE);
                    imageView_medal.setImageResource(R.drawable.ui_view_jin);
                    break;
                case 1:
                    imageView_medal.setVisibility(View.VISIBLE);
                    textView_rank_num.setVisibility(View.INVISIBLE);
                    imageView_medal.setImageResource(R.drawable.ui_view_yin);
                    break;
                case 2:
                    imageView_medal.setVisibility(View.VISIBLE);
                    textView_rank_num.setVisibility(View.INVISIBLE);
                    imageView_medal.setImageResource(R.drawable.ui_view_tong);
                    break;
                default:
                    imageView_medal.setVisibility(View.INVISIBLE);
                    textView_rank_num.setVisibility(View.VISIBLE);
                    textView_rank_num.setText(position + "");

                    break;
            }
            if (rankPresenter.getUserId(position).equals(DataUtils.getInstance().userInfo.getId())){
                imageView_rank.setImageResource(R.drawable.ui_item_lock);
            }
            textView_rank.setText(rankPresenter.getNickName(position));
            return v;
        }
    }
}