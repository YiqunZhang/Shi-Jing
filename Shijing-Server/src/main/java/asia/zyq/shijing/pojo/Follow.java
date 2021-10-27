package asia.zyq.shijing.pojo;



import java.util.Objects;


public class Follow {
    private String userId;
    private String followId;

    public Follow() {
    }

    public Follow(String userId, String followId) {
        this.userId = userId;
        this.followId = followId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return Objects.equals(userId, follow.userId) &&
                Objects.equals(followId, follow.followId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, followId);
    }

    @Override
    public String toString() {
        return "Follow{" +
                "userId='" + userId + '\'' +
                ", followId='" + followId + '\'' +
                '}';
    }
}
