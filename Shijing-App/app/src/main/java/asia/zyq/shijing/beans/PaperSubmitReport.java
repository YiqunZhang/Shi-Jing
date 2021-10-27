package asia.zyq.shijing.beans;

public class PaperSubmitReport {
    private String username;
    private String paperId;
    private Integer correctNumber;
    private Integer totalNumber;
    private Integer money;
    private Integer star;

    private Integer totalMoney;
    private Integer totalEnergy;

    public PaperSubmitReport() {
    }

    public PaperSubmitReport(String username, String paperId, Integer correctNumber, Integer totalNumber, Integer money, Integer star, Integer totalMoney, Integer totalEnergy) {
        this.username = username;
        this.paperId = paperId;
        this.correctNumber = correctNumber;
        this.totalNumber = totalNumber;
        this.money = money;
        this.star = star;
        this.totalMoney = totalMoney;
        this.totalEnergy = totalEnergy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public Integer getCorrectNumber() {
        return correctNumber;
    }

    public void setCorrectNumber(Integer correctNumber) {
        this.correctNumber = correctNumber;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(Integer totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    @Override
    public String toString() {
        return "PaperSubmitReport{" +
                "username='" + username + '\'' +
                ", paperId='" + paperId + '\'' +
                ", correctNumber=" + correctNumber +
                ", totalNumber=" + totalNumber +
                ", money=" + money +
                ", star=" + star +
                ", totalMoney=" + totalMoney +
                ", totalEnergy=" + totalEnergy +
                '}';
    }
}
