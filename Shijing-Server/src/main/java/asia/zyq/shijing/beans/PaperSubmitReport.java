package asia.zyq.shijing.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperSubmitReport {
    private String username;
    private String paperId;
    private Integer correctNumber;
    private Integer TotalNumber;
    private Integer money;
    private Integer star;

    private Integer totalMoney;
    private Integer totalEnergy;



}
