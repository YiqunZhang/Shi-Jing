package asia.zyq.shijing.pojo;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String id;
    private String userId;
    private String paperId;
    private Integer star;
    private Boolean virgin;
    private Boolean unlock;
    private Integer money;


}
