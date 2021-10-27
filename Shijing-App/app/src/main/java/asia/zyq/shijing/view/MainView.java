package asia.zyq.shijing.view;

import asia.zyq.shijing.beans.UserInfo;

public interface MainView {
    public void toast(String text);
    public void statusLoading();
    public void statusDefault();
    public void gotoListActivity();
    public void gotoQuestionActivity();
    public void showUserInfo(UserInfo userInfo);
    public void noticeCheck();
}
