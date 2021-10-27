package asia.zyq.shijing.beans;

public class Chapter extends ListBean{
    private String id;
    private String name;
    private Integer number;

    public Chapter(String id, String name, Integer number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public Chapter() {
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
