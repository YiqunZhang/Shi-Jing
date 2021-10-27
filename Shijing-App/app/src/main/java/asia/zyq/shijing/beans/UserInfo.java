package asia.zyq.shijing.beans;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class UserInfo {

    public UserInfo() {

    }

    public UserInfo(String username, String password, String nickname, Integer money, Integer energy, String id, Integer star, String notice) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.money = money;
        this.energy = energy;
        this.id = id;
        this.star = star;
    }

    private String username;
    private String password;
    private String nickname;
    private Integer money;
    private Integer energy;
    private String id;
    private Integer star;
    private String notice;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", money=" + money +
                ", energy=" + energy +
                ", id='" + id + '\'' +
                ", star=" + star +
                ", notice='" + notice + '\'' +
                '}';
    }
}
