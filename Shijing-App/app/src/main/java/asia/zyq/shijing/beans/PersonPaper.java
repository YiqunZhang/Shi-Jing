package asia.zyq.shijing.beans;

import asia.zyq.shijing.beans.ListBean;

public class PersonPaper extends ListBean {
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

    public PersonPaper() {

    }


    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Boolean getVirgin() {
        return virgin;
    }

    public void setVirgin(Boolean virgin) {
        this.virgin = virgin;
    }

    @Override
    public Boolean getUnlock() {
        return unlock;
    }

    public void setUnlock(Boolean unlock) {
        this.unlock = unlock;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubsectionId() {
        return subsectionId;
    }

    public void setSubsectionId(String subsectionId) {
        this.subsectionId = subsectionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getDefaultunlock() {
        return defaultunlock;
    }

    public void setDefaultunlock(Boolean defaultunlock) {
        this.defaultunlock = defaultunlock;
    }

    @Override
    public String getId() {
        return getPaperId();
    }

    public PersonPaper(String personId, String userId, String paperId, Integer star, Boolean virgin, Boolean unlock, Integer money, String name, String subsectionId, String type, Integer number, Boolean defaultunlock, Integer needEnergy, Integer totalNumber) {
        this.personId = personId;
        this.userId = userId;
        this.paperId = paperId;
        this.star = star;
        this.virgin = virgin;
        this.unlock = unlock;
        this.money = money;
        this.name = name;
        this.subsectionId = subsectionId;
        this.type = type;
        this.number = number;
        this.defaultunlock = defaultunlock;
        this.needEnergy = needEnergy;
        this.totalNumber = totalNumber;
    }

    public Integer getNeedEnergy() {
        return needEnergy;
    }

    public void setNeedEnergy(Integer needEnergy) {
        this.needEnergy = needEnergy;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    @Override
    public String toString() {
        return "PersonPaper{" +
                "personId='" + personId + '\'' +
                ", userId='" + userId + '\'' +
                ", paperId='" + paperId + '\'' +
                ", star=" + star +
                ", virgin=" + virgin +
                ", unlock=" + unlock +
                ", money=" + money +
                ", name='" + name + '\'' +
                ", subsectionId='" + subsectionId + '\'' +
                ", type='" + type + '\'' +
                ", number=" + number +
                ", defaultunlock=" + defaultunlock +
                ", needEnergy=" + needEnergy +
                ", totalNumber=" + totalNumber +
                '}';
    }
}
