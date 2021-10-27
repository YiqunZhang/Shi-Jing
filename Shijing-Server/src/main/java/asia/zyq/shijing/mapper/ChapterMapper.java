package asia.zyq.shijing.mapper;

import asia.zyq.shijing.pojo.Chapter;
import asia.zyq.shijing.pojo.Subsection;

import java.util.List;

public interface ChapterMapper {
    public List<Chapter> getChapterList();
    public Chapter getChapterById(String id);
}
