package asia.zyq.shijing.beans;

public class UserRankInfo {
    private String username;
    private String userId;
    private String nickname;
    private Integer money;
    private Integer star;

    public UserRankInfo() {
    }

    public UserRankInfo(String username, String userId, String nickname, Integer money, Integer star) {
        this.username = username;
        this.userId = userId;
        this.nickname = nickname;
        this.money = money;
        this.star = star;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return "UserRankInfo{" +
                "username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", money=" + money +
                ", star=" + star +
                '}';
    }
}
