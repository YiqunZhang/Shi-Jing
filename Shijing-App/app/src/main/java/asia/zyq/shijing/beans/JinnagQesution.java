package asia.zyq.shijing.beans;

public class JinnagQesution {
    private String id;
    private String title;
    private String text;
    private String type;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String paperid;
    private Integer number;
    private String analysis;
    private Integer totalMoney;
    private Integer addMoney;
    private Boolean correct;

    public JinnagQesution() {

    }

    public JinnagQesution(String id, String title, String text, String type, String optionA, String optionB, String optionC, String optionD, String answer, String paperid, Integer number, String analysis, Integer totalMoney, Integer addMoney, Boolean correct) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.type = type;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.paperid = paperid;
        this.number = number;
        this.analysis = analysis;
        this.totalMoney = totalMoney;
        this.addMoney = addMoney;
        this.correct = correct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPaperid() {
        return paperid;
    }

    public void setPaperid(String paperid) {
        this.paperid = paperid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(Integer addMoney) {
        this.addMoney = addMoney;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "JinnagQesution{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", answer='" + answer + '\'' +
                ", paperid='" + paperid + '\'' +
                ", number=" + number +
                ", analysis='" + analysis + '\'' +
                ", totalmMoney=" + totalMoney +
                ", addMoney=" + addMoney +
                ", correct=" + correct +
                '}';
    }

    public Question getQuestion(){
        return new Question( id, title, text, type, optionA, optionB, optionC, optionD, answer, paperid, number, analysis);
    }
}
