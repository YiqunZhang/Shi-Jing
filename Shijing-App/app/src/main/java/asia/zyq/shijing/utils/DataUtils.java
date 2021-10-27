package asia.zyq.shijing.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import asia.zyq.shijing.beans.UserInfo;

public class DataUtils {
    SharedPreferences sp = null;

    private static DataUtils instance = new DataUtils();
    private DataUtils(){
    }

    public void setSp(SharedPreferences sp){
        this.sp = sp;
    }
    private SharedPreferences getSp(){
        return sp;
    }
    public static DataUtils getInstance(){
        return instance;
    }

    private final String DOMAIN = "http://server.zyq.asia";
    private final Integer PORT = 8080;

    public UserInfo userInfo = null;

    public ListType listTypeSender = ListType.CHAPTER;
    public String itemIdSender = "";
    public QuestionType questionType = QuestionType.JVQING;
    public String paperIdSender = "";

    public String getStringFromFile(String key){
        if (getSp() == null){
            return "";
        }
        return getSp().getString(key,"");
    }

    public void putStringToFile(String key, String value){
        SharedPreferences.Editor editor = getSp().edit();
        editor.putString(key,value);
        editor.commit();

    }




    public void get(final NetParams netParams){
        new Thread(){
            @Override
            public void run() {

                HttpURLConnection httpURLConnection = null;
                BufferedReader reader = null;

                String path = DOMAIN+":"+PORT+"/"+netParams.funcName+"?";
                for (Map.Entry<String, String> para : netParams.params.entrySet()) {
                    path += para.getKey() + "=" + para.getValue() + "&";
                }
                path = path.substring(0,path.length()-1);
                //System.out.println(path);
                try {
                    URL url = new URL(path);
                    System.out.println(path);
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.connect();
                    int code = httpURLConnection.getResponseCode();
                    //System.out.println(code);
                    if (code == 200){

                        InputStream inputStream = httpURLConnection.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine())!=null){
                            result.append(line);
                        }
                        System.out.println(result);
                        netParams.callback.onSuccess(result.toString());
                        return;
                    }else {
                        netParams.callback.onError(code);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (httpURLConnection != null){
                        httpURLConnection.disconnect();
                    }
                    try {
                        if (reader != null){
                            reader.close();
                        }
                    }catch (Exception e){

                        e.printStackTrace();
                    }finally {

                    }
                }
                netParams.callback.onError(-1);

            }

        }.start();

    }
}
