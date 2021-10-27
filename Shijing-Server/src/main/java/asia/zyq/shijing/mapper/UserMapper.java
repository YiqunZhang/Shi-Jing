package asia.zyq.shijing.mapper;

import asia.zyq.shijing.beans.ParamsTwoInt;
import asia.zyq.shijing.pojo.User;

import java.util.List;

public interface UserMapper {
    public List<User> getUserList();
    public User getUserByUsername(String username);
    public User getUserById(String id);
    public void insetUser(User user);
    public void updateUser(User user);
    public void addUserEnergy(ParamsTwoInt paramsTwoInt);

}
