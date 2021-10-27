package asia.zyq.shijing.utils;

import java.util.Map;

public class NetParams {

    public NetParams(String funcName, Map<String, String> params, Callback callback) {
        this.funcName = funcName;
        this.params = params;
        this.callback = callback;
    }
    public String funcName;
    public Map<String,String> params;
    public Callback callback;
}
