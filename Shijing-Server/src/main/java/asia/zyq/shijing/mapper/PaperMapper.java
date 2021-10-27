package asia.zyq.shijing.mapper;

import asia.zyq.shijing.pojo.Paper;

import java.util.List;


public interface PaperMapper {
    public List<Paper> getPaperListBySubsectionId(String subsectionId);
    public Paper getPaperById(String id);


}
