package asia.zyq.shijing.view;

public interface SignInView {
    public void toast(String text);
    public void statusSignInLoading();
    public void statusDefault();
    public String getUsername();
    public String getPassword();
    public void gotoSignUpActivity();
}
