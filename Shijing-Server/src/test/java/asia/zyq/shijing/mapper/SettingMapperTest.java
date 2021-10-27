package asia.zyq.shijing.mapper;

import asia.zyq.shijing.pojo.Setting;
import asia.zyq.shijing.pojo.User;
import asia.zyq.shijing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class SettingMapperTest {
    @Test
    public void getSettingByKey(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SettingMapper settingMapper = sqlSession.getMapper(SettingMapper.class);
        Setting setting = settingMapper.getSettingByKey("default_money");

        System.out.println(setting);

        sqlSession.close();
    }

}
