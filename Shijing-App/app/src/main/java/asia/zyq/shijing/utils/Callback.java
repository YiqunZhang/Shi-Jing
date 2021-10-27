package asia.zyq.shijing.utils;

public interface Callback {
    public void onSuccess(String result);
    public void onError(Integer code);
}
