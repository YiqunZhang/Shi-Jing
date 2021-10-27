package asia.zyq.shijing.view;

public interface MyListView {
    public void toast(String text);
    public void statusLoading();
    public void statusDefault();
    public void gotoListActivity();
    public void LoadSuccess();
    public void gotoQuestionActivity();
    public void refreshAdapter();
}
