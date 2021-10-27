package asia.zyq.shijing.mapper;

import asia.zyq.shijing.beans.ParamsTwoString;
import asia.zyq.shijing.pojo.Person;

import java.util.List;

public interface PersonMapper {
    public List<Person> getPersonListByUserId(String userId);
    public Person getPersonByUserIdPaperId(ParamsTwoString paramsTwoString);
    public void insertPerson(Person person);
    public void updatePerson(Person person);
    public List<Person> getPersonListByUserIdSubsectionId(ParamsTwoString paramsTwoString);

}
