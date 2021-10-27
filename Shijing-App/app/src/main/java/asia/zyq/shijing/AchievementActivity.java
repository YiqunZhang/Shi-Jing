package asia.zyq.shijing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import asia.zyq.shijing.utils.DataUtils;
import asia.zyq.shijing.view.AchievementView;

public class AchievementActivity extends AppCompatActivity implements AchievementView {

    ListView listView = null;
    TextView textView_user = null;

    Integer starNumberList[] = new Integer[]{1,5,10,20,30,50,100,150,200};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        listView = findViewById(R.id.listView_achievement);
        textView_user = findViewById(R.id.textView_user);
        textView_user.setText(DataUtils.getInstance().userInfo.getNickname() + "(" + DataUtils.getInstance().userInfo.getUsername() + ")");
        listView.setAdapter(new ThisListAdapter());

    }



    private class ThisListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return starNumberList.length;

        }

        public String getText(Integer position){
            return starNumberList[position] + "颗星星";
        }

        public Boolean getGet(Integer position){
            Integer number = starNumberList[position];
            if (DataUtils.getInstance().userInfo.getStar() >= number){
                return true;
            }else {
                return false;
            }
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

            View v = View.inflate(AchievementActivity.this,R.layout.activity_achievement_item,null);
            TextView textView_achievement = v.findViewById(R.id.textView_achievement);
            ImageView imageView = v.findViewById(R.id.imageView_get);
            if (getGet(position)){
                imageView.setImageResource(R.drawable.ui_item_get);
            }else {
                imageView.setImageResource(R.drawable.ui_item_notget);
            }
            textView_achievement.setText(getText(position));
            return v;
        }
    }
}