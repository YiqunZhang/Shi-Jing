package asia.zyq.shijing.utils;

import asia.zyq.shijing.beans.*;
import asia.zyq.shijing.mapper.*;
import asia.zyq.shijing.pojo.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.*;


@Controller
public class MainUtils {

    private String randomCode = "";
    private static MainUtils instance = new MainUtils();

    private MainUtils() {
        randomCode = getRandomInteger(2147483600) + "SJ";

    }

    public static MainUtils getInstance() {
        return instance;
    }

    public static Logger logger = LoggerFactory.getLogger(MainUtils.class);

    public List<Question> jinnangQuestionList = null;

    @ResponseBody
    @RequestMapping("/signUp")
    public String signUp(String username, String password, String nickname, String verificationCode) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserByUsername(username);
        if (user != null) {
            sqlSession.close();
            return "用户已存在";
        } else if (!checkVerificationCode(username, verificationCode)) {
            return "验证码错误或失效";
        } else {
            user = new User(username, password, nickname, Integer.parseInt(
                    getSetting("default_money")),
                    Integer.parseInt(getSetting("default_energy")),
                    UUID.randomUUID().toString());
            userMapper.insetUser(user);
            sqlSession.commit();
            sqlSession.close();
            return "SUCCESS";

        }
    }

    public String getCodeByTimeUsername(String username, Integer sub) {
        Long timeCode = System.currentTimeMillis() / 1000000 - sub;
        String code = DigestUtils.md5DigestAsHex((timeCode + "-" + randomCode).getBytes()).substring(2, 9);
        code = new BigInteger(code, 16).toString(10).substring(0, 6);
        return code;

    }

    public List<Question> getJinnangQuestionList() {
        jinnangQuestionList = getQuestionListByPaperIdSorted("00000000-0000-0000-0000-000000000000");
        return jinnangQuestionList;
    }


    public Question getNextJinnangQuestion(String username) {
        //验证过用户名和密码
        List<Question> questionList = getJinnangQuestionList();
        if (questionList.size() <= 0) {
            return null;
        } else {
            return questionList.get(getRandomInteger(questionList.size()));
        }


    }

    @ResponseBody
    @RequestMapping("/submitGetJinnangQuestion")
    public JinnagQesution submitGetJinnangQuestion(String username, String password, String quesitonId, String answer) {
        if (!checkPassword(username, password).equals("SUCCESS_成功")) {
            return null;
        }

        Integer addMoney = 0;
        Boolean correct = false;
        User user = getUserByUsername(username);

        if (!quesitonId.equals("")) {
            Question question = getQuestionById(quesitonId);
            if (question != null) {
                if (question.getAnswer().equals(answer)) {
                    addMoney = Integer.parseInt(getSetting("jinnang_money"));
                    correct = true;
                    user.setMoney(user.getMoney() + addMoney);
                }
            }
        }

        updateUser(user);
        Question question = getNextJinnangQuestion(username);
        JinnagQesution jinnagQesution = new JinnagQesution(question, user.getMoney(), addMoney, correct);
        return jinnagQesution;

    }

    public static Integer getRandomInteger(Integer maxNotInclude) {
        //不包括最大值
        Random r = new Random();
        return r.nextInt(maxNotInclude);
    }


    @ResponseBody
    @RequestMapping("/applyVerificationCode")
    public String applyVerificationCode(String username) {

        if (SmsMessage.send(username, getCodeByTimeUsername(username, 0))) {
            return "SUCCESS";
        } else {
            return "申请短信时发生错误";
        }


    }

    public Boolean checkVerificationCode(String username, String verificationCode) {
        String code1 = getCodeByTimeUsername(username, 0);
        String code2 = getCodeByTimeUsername(username, 1);
        if (verificationCode.equals(code1) || verificationCode.equals(code2)) {
            return true;
        } else {
            return false;
        }

    }

    @ResponseBody
    @RequestMapping("/signIn")
    public Object signIn(String username, String password) {
        String res = checkPassword(username, password);
        if (res.equals("SUCCESS_成功")) {
            return getUserInfoByUsername(username, password);
        } else {
            return res;
        }

    }


    @ResponseBody
    @RequestMapping("/getChapterListSorted")
    public List<Chapter> getChapterListSorted() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ChapterMapper chapterMapper = sqlSession.getMapper(ChapterMapper.class);
        List<Chapter> chapterList = chapterMapper.getChapterList();
        sqlSession.close();
        chapterList.sort(Comparator.comparing(Chapter::getNumber));

        return chapterList;
    }

    @ResponseBody
    @RequestMapping("/getSubsectionListByChapterIdSorted")
    public List<Subsection> getSubsectionListByChapterIdSorted(String chapterId) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SubsectionMapper subsectionMapper = sqlSession.getMapper(SubsectionMapper.class);
        List<Subsection> subsectionList = subsectionMapper.getSubsectionListByChapterId(chapterId);
        sqlSession.close();
        subsectionList.sort(Comparator.comparing(Subsection::getNumber));
        return subsectionList;
    }


    public List<Paper> getPaperListBySubsectionIdSorted(String subsectionId) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PaperMapper paperMapper = sqlSession.getMapper(PaperMapper.class);
        List<Paper> paperList = paperMapper.getPaperListBySubsectionId(subsectionId);
        sqlSession.close();
        paperList.sort(Comparator.comparing(Paper::getNumber));
        return paperList;
    }


    public List<Question> getQuestionListByPaperIdSortedNoAnswer(String paperId) {
        return hideQuestionListAnsert(getQuestionListByPaperIdSorted(paperId));
    }

    public String getSetting(String key) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SettingMapper settingMapper = sqlSession.getMapper(SettingMapper.class);
        Setting setting = settingMapper.getSettingByKey(key);
        sqlSession.close();
        if (setting == null) {
            return null;
        } else {
            return setting.getValue();
        }
    }

    public User getUserByUsername(String username) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserByUsername(username);
        sqlSession.close();
        return user;
    }

    public Question getQuestionById(String id) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
        Question question = questionMapper.getQuestionById(id);
        sqlSession.close();
        return question;
    }

    public String checkPassword(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            return "用户名不存在";
        } else if (!user.getPassword().equals(password)) {
            return "密码错误";
        } else {
            return "SUCCESS_成功";
        }
    }

    public Question hideQuestionAnswer(Question question) {
        question.setAnswer("");
        return question;
    }

    public List<Question> hideQuestionListAnsert(List<Question> questionList) {
        for (Question question : questionList) {
            hideQuestionAnswer(question);
        }
        return questionList;

    }

    @ResponseBody
    @RequestMapping("/getQuestionListByPaperIdSorted")
    public List<Question> getQuestionListByPaperIdSorted(String paperId) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
        List<Question> questionList = questionMapper.getQuestionListByPaperId(paperId);
        questionList.sort(Comparator.comparing(Question::getNumber));
        sqlSession.close();
        return questionList;
    }

    public List<Person> getPersonListByUserId(String userId) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        List<Person> personpaperList = personMapper.getPersonListByUserId(userId);
        sqlSession.close();
        return personpaperList;
    }

    @ResponseBody
    @RequestMapping("/getPersonPaperListByUserIdSubsectionIdSorted")
    public List<PersonPaper> getPersonPaperListByUserIdSubsectionIdSorted(String username, String password, String subsectionId) {
        if (!checkPassword(username, password).equals("SUCCESS_成功")) {
            return null;
        }
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonPaperMapper personPaperMapper = sqlSession.getMapper(PersonPaperMapper.class);
        List<PersonPaper> personPaperList = personPaperMapper.getPersonPaperListByUserIdSubsectionId(new ParamsTwoString(getUserByUsername(username).getId(), subsectionId));
        sqlSession.close();
        personPaperList.sort(Comparator.comparing(PersonPaper::getNumber));
        for (PersonPaper personPaper : personPaperList) {
            if (personPaper.getUserId() == null) {
                personPaper.setStar(0);
                personPaper.setVirgin(true);
                personPaper.setUnlock(false);
                personPaper.setPersonId(UUID.randomUUID().toString());
                personPaper.setUserId(getUserByUsername(username).getId());
                personPaper.setMoney(getPaperById(personPaper.getPaperId()).getMoney());

            }
            personPaper.setNeedEnergy(getEnergy(getPaperById(personPaper.getPaperId())));

            Integer totalNumber = 0;
            List<Question> questionList = getQuestionListByPaperIdSorted(personPaper.getPaperId());
            for (Question question : questionList) {
                if (!question.getAnswer().equals("")) {
                    totalNumber++;
                }
            }
            personPaper.setTotalNumber(totalNumber);
        }
        return personPaperList;

    }

    public Paper getPaperById(String paperId) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PaperMapper paperMapper = sqlSession.getMapper(PaperMapper.class);
        Paper paper = paperMapper.getPaperById(paperId);
        sqlSession.close();
        return paper;
    }

    @ResponseBody
    @RequestMapping("/getUserInfo")
    public UserInfo getUserInfoByUsername(String username, String password) {
        if (checkPassword(username, password) != "SUCCESS_成功") {
            return null;
        }
        User user = getUserByUsername(username);
        UserInfo userInfo = new UserInfo(
                user.getUsername(),
                user.getPassword(),
                user.getNickname(),
                user.getMoney(),
                user.getEnergy(),
                user.getId(),
                getStarNumberByUsername(username),
                getSetting("notice")
        );
        return userInfo;
    }


    @ResponseBody
    @RequestMapping("/submitPaper")
    public PaperSubmitReport submitPaper(String username, String password, String paperId, String answer) {

        Paper paper = getPaperById(paperId);
        User user = getUserByUsername(username);
        //检查用户名、密码、试卷、精力值
        if (checkEnergy(username, password, paperId) != "SUCCESS_成功") {
            return null;
        }
        List<Question> questionList = getQuestionListByPaperIdSorted(paperId);

        //获取提交答案列表
        List<String> answerSubmitList = new ArrayList<String>();
        for (int i = 0; i < answer.length(); i++) {
            answerSubmitList.add(answer.substring(i, i + 1));
        }

        //获取正确答案列表
        List<String> answerCorrectList = new ArrayList<String>();
        for (Question question : questionList) {
            String temp = question.getAnswer();
            if (temp.equals("")) {
                temp = "_";
            }
            answerCorrectList.add(temp);
        }

        //比较提交答案的长度
        if (answerSubmitList.size() != answerCorrectList.size()) {
            return null;
        }

        //检查是否解锁
        Person person = getPersonByUserIdPaperId(user.getId(), paperId);
        if (person == null) {
            return null;
        } else if (!person.getUnlock()) {
            return null;
        }

        //比对答案
        Integer correctNumber = 0;
        Integer totalNumber = 0;
        for (int i = 0; i < answer.length(); i++) {
            if (!answerCorrectList.get(i).equals("_")) {
                totalNumber++;
                //System.out.println("正确长度:" + answerCorrectList.get(i).length());
                //System.out.println("提交长度:" + answerSubmitList.get(i).length());
                String tempCorrect = answerCorrectList.get(i).trim();
                String tempSubmit = answerSubmitList.get(i).trim();

                if (tempCorrect.equals(tempSubmit)) {
                    correctNumber++;
                }
            }

        }

        PaperSubmitReport paperSubmitReport = new PaperSubmitReport(username, paperId, correctNumber, totalNumber, 0, 0, 0, 0);
        //检查是否做过 并且设置银两值
        Integer money = 0;
        if (person.getVirgin()) {
            person.setVirgin(false);
            if (paper.getType().equals("主线")) {
                money = Integer.parseInt(getSetting("zhuxian_money")) * correctNumber;
            } else if (paper.getType().equals("番外")) {
                money = Integer.parseInt(getSetting("fanwai_money")) * correctNumber;
            }
        } else {
            money = 0;
        }
        paperSubmitReport.setMoney(money);
        person.setMoney(money);
        user.setMoney(user.getMoney() + money);

        // 设置试卷获得的星星
        Integer star = 0;
        if (paper.getType().equals("主线")) {
            star = Integer.parseInt(getSetting("zhuxian_star")) * correctNumber;
        } else if (paper.getType().equals("番外")) {
            star = Integer.parseInt(getSetting("fanwai_star")) * correctNumber;
        }
        paperSubmitReport.setStar(star);
        person.setStar(star);

        //更新用户精力值
        user.setEnergy(user.getEnergy() - (totalNumber - correctNumber));
        // 自动解锁
        if (correctNumber > 0 || totalNumber == 0) {
            unlockPaperNext(username, paperId, "主线");

        }

        //更新现在的状态
        paperSubmitReport.setTotalEnergy(user.getEnergy());
        paperSubmitReport.setTotalMoney(user.getMoney());

        //提交结算
        updateUser(user);
        updatePerson(person);
        return paperSubmitReport;

    }

    public Integer getEnergy(Paper paper) {
        Integer totalNumber = 0;
        List<Question> questionList = getQuestionListByPaperIdSorted(paper.getId());
        for (Question question : questionList) {
            if (question.getAnswer() != "" && question.getAnswer() != "_") {
                totalNumber++;
            }
        }
        return totalNumber;
    }

    public String checkEnergy(String username, String password, String paperId) {
        Paper paper = getPaperById(paperId);
        User user = getUserByUsername(username);
        if (checkPassword(username, password) != "SUCCESS_成功") {
            return checkPassword(username, password);
        } else if (paper == null) {
            return "FAIL_试卷不存在";
        }
        Integer totalNumber = getEnergy(paper);

        if (totalNumber > user.getEnergy()) {
            return "FAIL_精力不足";
        } else {
            return "SUCCESS_成功";
        }

    }

    public Paper getNextPaper(String paperIdOriginal) {
        Paper originalPaper = getPaperById(paperIdOriginal);
        if (originalPaper == null) {
            return null;
        }
        List<Paper> paperList = getPaperListBySubsectionIdSorted(originalPaper.getSubsectionId());

        Boolean ready = false;

        for (Paper paper : paperList) {
            if (ready) {
                return paper;
            }
            if (paper.getId().equals(paperIdOriginal)) {
                ready = true;
            }
        }
        return null;
    }


    public String unlockPaperNext(String username, String paperIdOriginal, String typeLimit) {
        Paper paperNext = getNextPaper(paperIdOriginal);
        if (typeLimit.length() > 0) {
            if (paperNext == null) {
                return "FAIL_试卷类型限制不符";
            } else if (!paperNext.getType().equals(typeLimit)) {
                return "FAIL_试卷类型限制不符";
            }

        }
        return unlockPaperThis(username, paperNext, true);
    }

    public String unlockPaperThis(String username, Paper paper, Boolean noCondition) {
        //验证过用户名和密码
        User user = getUserByUsername(username);
        Boolean unlockSuccess = false;

        if (paper == null) {
            return "试卷不存在";
        } else if (paper.getDefaultunlock()) {
            //试卷默认解锁
            unlockSuccess = true;
        } else if (paper.getUnlockstarlast() > 0) {
            //上一小节星数总和解锁
            Integer statLastSubsection = getLastSubsectonStarByUserIdPaperId(user.getId(), paper.getId());
            if (statLastSubsection == null) {

                return "上一小节星数获取发生错误";
            }
            if (statLastSubsection >= paper.getUnlockstarlast()) {
                unlockSuccess = true;
            }else{
            }


        } else if (paper.getMoney() > 0) {
            if (user.getMoney() >= paper.getMoney()) {
                //银两值解锁
                unlockSuccess = true;
            }
        } else if (noCondition) {
                //调用函数要求强制解锁
            unlockSuccess = true;
        }if (unlockSuccess){
            //创建或更新person
            Person person = getPersonByUserIdPaperId(user.getId(), paper.getId());
            if (person != null) {
                person.setUnlock(true);
                updatePerson(person);
            } else {
                person = new Person(UUID.randomUUID().toString(),
                        user.getId(), paper.getId(),
                        0, true, true, 0);
                insertPerson(person);

            }
            //扣除银两值
            user.setMoney(user.getMoney() - paper.getMoney());
            updateUser(user);
            return "SUCCESS_成功";
        }else {
            return "解锁条件不足(" + paper.getUnlockCondition() +")";
        }


    }

    @ResponseBody
    @RequestMapping("/unlockPaper")
    public String unlockPaper(String username, String password, String paperId) {
        if (checkPassword(username, password) != "SUCCESS_成功") {
            return checkPassword(username, password);
        } else {
            return unlockPaperThis(username, getPaperById(paperId), false);
        }


    }

    public void updateUser(User user) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.updateUser(user);
        sqlSession.commit();
        sqlSession.close();
    }


    public void insertPerson(Person person) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        personMapper.insertPerson(person);
        sqlSession.commit();
        sqlSession.close();

    }

    public void updatePerson(Person person) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        personMapper.updatePerson(person);
        sqlSession.commit();
        sqlSession.close();

    }

    public Person getPersonByUserIdPaperId(String userId, String paperId) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        Person person = personMapper.getPersonByUserIdPaperId(new ParamsTwoString(userId, paperId));
        sqlSession.close();
        return person;

    }

    public Integer getStarNumberByUsername(String username) {
        //公开信息，不需要验证密码
        Integer res = 0;
        List<Person> personList = getPersonListByUserId(getUserByUsername(username).getId());
        for (Person person : personList) {
            res += person.getStar();
        }
        return res;
    }

    public Integer getMoneyByUsername(String username) {
        //公开信息，不需要验证密码
        User user = getUserByUsername(username);
        if (user == null) {
            return null;
        } else {
            return user.getMoney();
        }
    }


    public void addUserEnergy(Integer addEnergy, Integer maxEnergy) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.addUserEnergy(new ParamsTwoInt(addEnergy,maxEnergy));
        sqlSession.commit();
        sqlSession.close();
    }

    public List<User> getUserList() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.getUserList();
        sqlSession.close();
        return userList;
    }

    @ResponseBody
    @RequestMapping("/getRankList")
    public List<UserRankInfo> getRankList() {
        List<User> userList = getUserList();
        List<UserRankInfo> userRankInfoList = new ArrayList<UserRankInfo>();
        for (User user : userList) {
            UserRankInfo userRankInfo = new UserRankInfo(
                    user.getUsername(),
                    user.getId(),
                    user.getNickname(),
                    user.getMoney(),
                    getStarNumberByUsername(user.getUsername())
            );
            userRankInfoList.add(userRankInfo);
        }


        userRankInfoList.sort((o1, o2) -> o2.getStar().compareTo(o1.getStar()));
        return userRankInfoList;
    }

    public Integer getLastSubsectonStarByUserIdPaperId(String userId, String paperId) {
        Subsection subsectionLast = getLastSubsectionByPaperId(paperId);
        if (subsectionLast == null) {
            return null;
        }
        System.out.println("FUNC-getLastSubsectonStarByUserIdPaperId:"+"上一小节 "+subsectionLast.getName());
        System.out.println(subsectionLast.getId() + "-");
        return getStarByUserIdSubsectionId(userId, subsectionLast.getId());
    }


    public Subsection getLastSubsectionByPaperId(String paperId) {
        Paper paper = getPaperById(paperId);
        if (paper == null) {
            return null;
        }
        Subsection subsectionOriginal = getSubsectionById(paper.getSubsectionId());
        if (subsectionOriginal == null) {
            return null;
        }

        List<Subsection> subsectionList = getSubsectionListByChapterIdSorted(subsectionOriginal.getChapterId());
        if (subsectionList == null) {
            return null;
        }

        Subsection subsectionLast = null;
        for (Subsection subsectionI : subsectionList) {
            if (subsectionI.getId().equals(subsectionOriginal.getId())) {
                return subsectionLast;
            } else {
                subsectionLast = subsectionI;
            }
        }

        return null;
    }

    public Integer getStarByUserIdSubsectionId(String userId, String subsectionId) {
        Integer res = 0;
        List<Person> personList = getPersonListByUserIdSubsectionId(userId, subsectionId);
        System.out.println("FUNC-getStarByUserIdSubsectionId:userid "+userId+" subsectionid "+subsectionId);
        System.out.println("FUNC-getStarByUserIdSubsectionId:个人试卷列表"+personList);

        for (Person person : personList) {
            res += person.getStar();
            System.out.println("FUNC-getStarByUserIdSubsectionId:"+"上一小节个人试卷星数"+person.getStar()+",试卷名称"+getPaperById(person.getPaperId()).getName());
        }
        return res;
    }

    public List<Person> getPersonListByUserIdSubsectionId(String userId, String subsectionId) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        List<Person> personList = personMapper.getPersonListByUserIdSubsectionId(new ParamsTwoString(
                userId,
                subsectionId));
        sqlSession.close();
        return personList;

    }

    public Subsection getSubsectionById(String id) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        SubsectionMapper subsectionMapper = sqlSession.getMapper(SubsectionMapper.class);
        Subsection subsection = subsectionMapper.getSubsectionById(id);
        sqlSession.close();
        return subsection;
    }

    public Chapter getChapterById(String id) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ChapterMapper chapterMapper = sqlSession.getMapper(ChapterMapper.class);
        Chapter chapter = chapterMapper.getChapterById(id);
        sqlSession.close();
        return chapter;
    }

    public List<Follow> getFollowListByUserId(String userId) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        List<Follow> followList = followMapper.getFollowListByUserId(userId);


        return followList;
    }

    public Follow getFollowByFollow(String userId, String followId) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        Follow follow = followMapper.getFollowByFollow(new Follow(userId, followId));

        sqlSession.close();
        return follow;
    }

    public void deleteFollow(Follow follow) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        followMapper.deleteFollow(follow);
        sqlSession.commit();
        sqlSession.close();
    }

    public void insertFollow(Follow follow) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        followMapper.insertFollow(follow);
        sqlSession.commit();
        sqlSession.close();
    }

    public void getFollowInfoListBy(String userId){
        //公开信息，不需要验证用户名和密码

    }

    public List<UserRankInfo> getFollowUserRankInfoListByUserId(String userId){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        List<UserRankInfo> userRankInfoList = followMapper.getFollowUserRankInfoListByUserId(userId);
        sqlSession.close();
        for (UserRankInfo userRankInfo : userRankInfoList) {
            userRankInfo.setStar(getStarNumberByUsername(userRankInfo.getUsername()));

        }
        return userRankInfoList ;

    }

    @ResponseBody
    @RequestMapping("/getFollowList")
    public List<UserRankInfo> getFollowList(String username, String password){
        User user = getUserByUsername(username);
        if (checkPassword(username,password)!="SUCCESS_成功"){
            return null;
        }


        return getFollowUserRankInfoListByUserId(user.getId());
    }

    @ResponseBody
    @RequestMapping("/addFollow")
    public Object addFollow(String username, String password, String usernameFollow){
        User user = getUserByUsername(username);
        String checkPasswordRes = checkPassword(username,password);
        if (checkPasswordRes!="SUCCESS_成功"){
            return checkPasswordRes;
        }
        User followedUser = getUserByUsername(usernameFollow);
        if (followedUser == null){
            return "被关注用户不存在";
        }


        try {
            insertFollow(new Follow(user.getId(),followedUser.getId()));
        }catch (Exception e){
            return "不能再关注这个用户了";
        }
        return getFollowUserRankInfoListByUserId(user.getId());
    }

    @ResponseBody
    @RequestMapping("/subFollow")
    public Object subFollow(String username, String password, String usernameFollow){

        String checkPasswordRes = checkPassword(username,password);
        User user = getUserByUsername(username);
        if (checkPasswordRes=="SUCCESS_成功"){

        }else {
            return checkPasswordRes;
        }
        User followedUser = getUserByUsername(usernameFollow);
        if (followedUser == null){
            return "被关注用户获取出错";
        }


        try {
            deleteFollow(new Follow(user.getId(),followedUser.getId()));
        }catch (Exception e){
            return "不能再取关这个用户了";
        }
        return getFollowUserRankInfoListByUserId(user.getId());
    }

}
