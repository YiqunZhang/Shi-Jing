package asia.zyq.shijing.mapper;

import asia.zyq.shijing.beans.ParamsTwoString;
import asia.zyq.shijing.beans.UserRankInfo;
import asia.zyq.shijing.pojo.Follow;

import java.util.List;

public interface FollowMapper {
    public List<Follow> getFollowListByUserId(String userid);
    public Follow getFollowByFollow(Follow follow);
    public void deleteFollow(Follow follow);
    public void insertFollow(Follow follow);
    public List<UserRankInfo> getFollowUserRankInfoListByUserId(String userid);

}
