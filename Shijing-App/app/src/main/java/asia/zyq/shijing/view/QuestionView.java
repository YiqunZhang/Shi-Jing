package asia.zyq.shijing.view;

import asia.zyq.shijing.beans.PaperSubmitReport;
import asia.zyq.shijing.beans.Question;

public interface QuestionView {
    public void showMultiple(Question question);
    public void showNoOptions(Question question);
    public void showAnalysis(Question question);
    public void showJvqingSettlement(PaperSubmitReport paperSubmitReport);
    public void back();
    public void toast(String text);

}
