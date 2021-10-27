package asia.zyq.shijing.pojo;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonPaper {
    private String personId;
    private String userId;
    private String paperId;
    private Integer star;
    private Boolean virgin;
    private Boolean unlock;
    private Integer money;
    private String name;
    private String subsectionId;
    private String type;
    private Integer number;
    private Boolean defaultunlock;
    private Integer needEnergy;
    private Integer totalNumber;

}
