package asia.zyq.shijing.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asia.zyq.shijing.beans.PersonPaper;
import asia.zyq.shijing.beans.Chapter;
import asia.zyq.shijing.beans.ListBean;
import asia.zyq.shijing.beans.Subsection;
import asia.zyq.shijing.utils.Callback;
import asia.zyq.shijing.utils.DataUtils;
import asia.zyq.shijing.utils.ListType;
import asia.zyq.shijing.utils.NetParams;
import asia.zyq.shijing.utils.NetParamsGetter;
import asia.zyq.shijing.utils.QuestionType;
import asia.zyq.shijing.view.MyListView;

public class ListPresenter implements NetParamsGetter {

    MyListView view;

    ListType listTypeSender = null;
    List<ListBean> itemList = null;
    String itemIdSender = "";

    Map<Integer,Integer> paperUnlockMap = new HashMap<Integer,Integer>();

    public ListPresenter(MyListView view){
        this.view = view;
        listTypeSender = DataUtils.getInstance().listTypeSender;
        itemIdSender = DataUtils.getInstance().itemIdSender;
        //refreshItemList();
    }
    public void refreshItemList(){
        if (listTypeSender == ListType.ROOT){
            DataUtils.getInstance().get(getNetParams(1,null));
        }else if(listTypeSender == ListType.CHAPTER) {
            DataUtils.getInstance().get(getNetParams(2,null));
        }else if(listTypeSender == ListType.SUBSECTION) {
            DataUtils.getInstance().get(getNetParams(3,null));
        }

    }

    public Integer getCount(){
        return itemList.size();
    }
    public String getId(Integer i){
        return itemList.get(i).getId();
    }
    public Boolean getUnlock(Integer i){
        return itemList.get(i).getUnlock();
    }
    public String getName(Integer i){
        return itemList.get(i).getName();
    }

    public Integer getStar(Integer i){
        return itemList.get(i).getStar();
    }
    public Integer getTotalNumber(Integer i){
        return itemList.get(i).getTotalNumber();
    }

    public void onItemClick(Integer position){
        if (listTypeSender == ListType.SUBSECTION){

            DataUtils.getInstance().questionType = QuestionType.JVQING;
            Integer energyNow = DataUtils.getInstance().userInfo.getEnergy();
            Integer energyNeed = ((PersonPaper)itemList.get(position)).getNeedEnergy();
            if (energyNow < energyNeed){
                view.toast("体力不足. 当前体力:"+energyNow+",所需最低体力:"+energyNeed);
            }else if (!itemList.get(position).getUnlock()){
                Integer unlockNumber = paperUnlockMap.get(position);
                if (unlockNumber == null){
                    view.toast("这个试卷尚未解锁，再次点击进行解锁");
                    paperUnlockMap.put(position,1);
                }else {
                    view.toast("正在申请解锁...");
                    DataUtils.getInstance().get(getNetParams(4,itemList.get(position).getId()));
                }


            }else {
                DataUtils.getInstance().paperIdSender = itemList.get(position).getId();
                view.gotoQuestionActivity();
            }



        }else {
            if (listTypeSender == ListType.ROOT){
                DataUtils.getInstance().listTypeSender = ListType.CHAPTER;

            }else if(listTypeSender == ListType.CHAPTER){
                DataUtils.getInstance().listTypeSender = ListType.SUBSECTION;
            }
            DataUtils.getInstance().itemIdSender = itemList.get(position).getId();
            view.gotoListActivity();
        }



    }


    @Override
    public NetParams getNetParams(Integer what, Object object) {
        if (what == 1 || what == 2 || what == 3){
            Map<String,String> map = new HashMap<String,String>();
            String funcName = "";
            if(what == 1){
                funcName = "getChapterListSorted";
            }else if (what == 2){
                funcName = "getSubsectionListByChapterIdSorted";
                map.put("chapterId",itemIdSender);
            }else if (what == 3){
                funcName = "getPersonPaperListByUserIdSubsectionIdSorted";
                map.put("username",DataUtils.getInstance().userInfo.getUsername());
                map.put("password",DataUtils.getInstance().userInfo.getPassword());
                map.put("subsectionId",itemIdSender);
            }

            return new NetParams(funcName, map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("[")){
                        List<ListBean> listBeanslist = new ArrayList<ListBean>();
                        if (listTypeSender == ListType.ROOT){
                            List<Chapter> chapterList = new Gson().fromJson(result, new TypeToken<List<Chapter>>(){}.getType());
                            for (Chapter chapter : chapterList) {
                                listBeanslist.add((ListBean)chapter);
                            }
                        }else if (listTypeSender == ListType.CHAPTER){
                            List<Subsection> subsections = new Gson().fromJson(result, new TypeToken<List<Subsection>>(){}.getType());
                            for (Subsection subsection : subsections) {
                                listBeanslist.add((ListBean)subsection);
                            }
                        }else if(listTypeSender == ListType.SUBSECTION){
                            List<PersonPaper> personPaperList = new Gson().fromJson(result, new TypeToken<List<PersonPaper>>(){}.getType());
                            for (PersonPaper personPaper : personPaperList) {
                                listBeanslist.add((ListBean)personPaper);
                            }
                        }

                        itemList = listBeanslist;
                        view.statusDefault();
                        view.LoadSuccess();
                        //view.refreshAdapter();

                    }else {
                        view.toast("发生错误");
                        view.statusDefault();
                    }
                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                    view.statusDefault();
                }
            });
        }else if(what == 4){
            Map<String,String> map = new HashMap<String,String>();
            map.put("username",DataUtils.getInstance().userInfo.getUsername());
            map.put("password",DataUtils.getInstance().userInfo.getPassword());
            map.put("paperId",(String)object);
            return new NetParams("unlockPaper", map, new Callback() {
                @Override
                public void onSuccess(String result) {
                    if (result.substring(0,1).equals("S")){
                        refreshItemList();
                    }else {
                        view.toast(result);
                    }
                }

                @Override
                public void onError(Integer code) {
                    if (code == -1){
                        view.toast("解锁时发生未知错误");
                    }else {
                        view.toast("网络错误，CODE"+code);
                    }
                    view.statusDefault();
                }
            });
        }else  {
            return null;
        }
    }
}
