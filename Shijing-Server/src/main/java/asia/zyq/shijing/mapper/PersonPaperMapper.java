package asia.zyq.shijing.mapper;

import asia.zyq.shijing.beans.ParamsTwoString;
import asia.zyq.shijing.pojo.PersonPaper;

import java.util.List;

public interface PersonPaperMapper {
    public List<PersonPaper> getPersonPaperListByUserIdSubsectionId(ParamsTwoString paramsTwoString);
}
