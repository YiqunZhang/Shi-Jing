package asia.zyq.shijing.mapper;

import asia.zyq.shijing.beans.ParamsTwoString;
import asia.zyq.shijing.pojo.Person;
import asia.zyq.shijing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class PersonMapperTest {
    @Test
    public void getPaperListBySubsectionId(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        List<Person> personpaperList = personMapper.getPersonListByUserId("15098144981");
        for (Person personpaper : personpaperList) {
            System.out.println(personpaper);
        }
        sqlSession.close();

    }

    @Test
    public void getPersonByUserIdPaperId(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        Person person = personMapper.getPersonByUserIdPaperId(new ParamsTwoString("15098144981","1"));
        System.out.println(person);
        sqlSession.close();

    }
    @Test
    public void insertPerson(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        personMapper.insertPerson(new Person(UUID.randomUUID().toString(),"150","1",10,true,false,10));
        sqlSession.commit();
        sqlSession.close();

    }
    @Test
    public void updatePerson(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        personMapper.updatePerson(new Person("1","15098144981","1",10,true,false,10));
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void getPersonListByUserIdSubsectionId(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        List<Person> personList = personMapper.getPersonListByUserIdSubsectionId(new ParamsTwoString(
                "8b2c5298-a383-4c2c-9eb5-c6cf36b65e0e",
                "b1538e01-39ff-46ab-9858-bedfeefc56ae"));
        System.out.println(personList);
        System.out.println(personList.size());
        sqlSession.close();

    }
}
