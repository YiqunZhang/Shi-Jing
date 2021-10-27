package asia.zyq.shijing.utils;

import android.app.Application;
import android.content.Context;

public class MyContextGetter extends Application {
    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
