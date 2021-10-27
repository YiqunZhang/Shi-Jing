package asia.zyq.shijing.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRankInfo {
    private String username;
    private String userId;
    private String nickname;
    private Integer money;
    private Integer star;


}
