package asia.zyq.shijing.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String username;
    private String password;
    private String nickname;
    private Integer money;
    private Integer energy;
    private String id;
    private Integer star;
    private String notice;


}
