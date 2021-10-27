package asia.zyq.shijing.beans;

public class Subsection extends ListBean {
    private String id;
    private String name;
    private String chapterId;
    private Integer number;

    public Subsection() {

    }

    public Subsection(String id, String name, String chapterId, Integer number) {
        this.id = id;
        this.name = name;
        this.chapterId = chapterId;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Subsection{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", number=" + number +
                '}';
    }

    @Override
    public String getId() {
        return id;
    }

    public Boolean getUnlock() {
        return true;
    }

    @Override
    public Integer getTotalNumber() {
        return -1;
    }

    @Override
    public Integer getStar() {
        return -1;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
