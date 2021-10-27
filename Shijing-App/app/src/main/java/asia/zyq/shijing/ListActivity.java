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

import asia.zyq.shijing.presenter.ListPresenter;
import asia.zyq.shijing.view.MyListView;

public class ListActivity extends AppCompatActivity implements MyListView, AdapterView.OnItemClickListener {

    ListPresenter listPresenter = null;
    ListView listView = null;
    Intent intent;
    BaseAdapter baseAdapter;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    baseAdapter = new ThisListAdapter();
                    listView.setAdapter(baseAdapter);
                    listView.setOnItemClickListener(ListActivity.this);
                    break;
                case 1:
                    Toast.makeText(ListActivity.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    intent = new Intent();
                    intent.setClass(ListActivity.this, ListActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 3:
                    listView.setEnabled(false);
                    break;
                case 4:
                    listView.setEnabled(true);
                    break;
                case 5:
                    intent = new Intent();
                    intent.setClass(ListActivity.this, QuestionActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 6:
                    baseAdapter.notifyDataSetChanged();
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onStart(){
        super.onStart();

        listPresenter.refreshItemList();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);

        listPresenter = new ListPresenter(this);


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
    public void LoadSuccess() {
        Message msg = new Message();
        msg.what = 0;
        handler.sendMessage(msg);
    }

    @Override
    public void gotoQuestionActivity() {
        Message msg = new Message();
        msg.what = 5;
        handler.sendMessage(msg);
    }

    @Override
    public void refreshAdapter() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listPresenter.onItemClick(position);
    }

    private class ThisListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listPresenter.getCount();
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
            View v = View.inflate(ListActivity.this,R.layout.activity_list_item,null);
            ImageView imageView_lock = v.findViewById(R.id.imageView_lock);
            ImageView imageView_list_star = v.findViewById(R.id.imageView_list_star);
            ImageView imageView_itembg = v.findViewById(R.id.imageView_itembg);
            if (listPresenter.getUnlock(position)){
                imageView_lock.setVisibility(View.INVISIBLE);
                imageView_itembg.setImageResource(R.drawable.ui_item_unlock);

            }else {
                imageView_lock.setVisibility(View.VISIBLE);
                imageView_itembg.setImageResource(R.drawable.ui_item_lock);
            }
            TextView textView_star = v.findViewById(R.id.textView_star);
            Integer totalNumber = listPresenter.getTotalNumber(position);
            Integer star = listPresenter.getStar(position);
            if (totalNumber > 0 && listPresenter.getUnlock(position)){
                textView_star.setText(star+"/"+totalNumber);
                imageView_list_star.setVisibility(View.VISIBLE);
                textView_star.setVisibility(View.VISIBLE);
            }else {
                imageView_list_star.setVisibility(View.INVISIBLE);
                textView_star.setVisibility(View.INVISIBLE);

            }
            TextView textView_chapterId = v.findViewById(R.id.textView_chapterId);
            TextView textView_chapterName = v.findViewById(R.id.textView_chapterName);
            textView_chapterId.setText(listPresenter.getId(position));
            textView_chapterName.setText(listPresenter.getName(position));
            return v;
        }
    }
}