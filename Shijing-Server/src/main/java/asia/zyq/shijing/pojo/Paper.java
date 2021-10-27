package asia.zyq.shijing.pojo;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper {
    private String id;
    private String name;
    private String subsectionId;
    private String type;
    private Integer money;
    private Integer number;
    private Boolean defaultunlock;
    private Integer unlockstarlast;
    private String unlockCondition;

}
