package asia.zyq.shijing.mapper;

import asia.zyq.shijing.pojo.Subsection;

import java.util.List;

public interface SubsectionMapper {
    public List<Subsection> getSubsectionListByChapterId(String chapterId);
    public Subsection getSubsectionById(String id);
}
