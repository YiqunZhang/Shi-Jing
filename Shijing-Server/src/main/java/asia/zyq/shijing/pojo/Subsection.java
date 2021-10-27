package asia.zyq.shijing.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subsection {
    private String id;
    private String name;
    private String chapterId;
    private Integer number;

}
