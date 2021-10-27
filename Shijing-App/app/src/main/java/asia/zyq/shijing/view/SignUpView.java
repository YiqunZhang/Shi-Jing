package asia.zyq.shijing.view;

public interface SignUpView {
    public void statusSignUpLoading();
    public void statusDefault();
    public void toast(String text);
    public void statusVerificationWait();

    public void gotoSignInActivity();

    public String getUsername();
    public String getNickname();
    public String getPassword();
    public String getPasswordRepeat();
    public String getVerification();
}
