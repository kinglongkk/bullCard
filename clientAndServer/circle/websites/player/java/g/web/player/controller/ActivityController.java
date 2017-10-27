package g.web.player.controller;

import g.model.activityapply.po.ActivityPlayerApply;
import g.model.activityapply.vo.ActivityPlayerApplyListVo;
import g.model.activitymessage.po.ActivityMessage;
import g.model.activitymessage.po.ActivityWayRelation;
import g.model.activitymessage.vo.ActivityMessageListVo;
import g.model.activitymessage.vo.ActivityWayRelationListVo;
import g.model.player.po.UserPlayer;
import g.service.activityapply.IActivityPlayerApplyService;
import g.service.activitymessage.IActivityMessageService;
import g.service.activitymessage.IActivityWayRelationService;
import g.service.common.IUserPlayerService;
import g.web.player.model.ResultData;
import g.web.player.session.SessionManager;
import org.soul.commons.lang.DateTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activity")
public class ActivityController{

    @Autowired
    private IActivityMessageService messageService;

    @Autowired
    private IActivityWayRelationService relationService;

    @Autowired
    private IActivityPlayerApplyService applyService;

    @Autowired
    private IUserPlayerService playerService;

    /**
     * 获取分享地址
     * @param request 请求
     * @return resultData
     */
    @RequestMapping("/uri")
    public Object share(HttpServletRequest request){
        String invitationCode = SessionManager.getSessionUserPlayer().getInvitationCode();
        String uri = "http://"+request.getServerName()+request.getContextPath()+"/share.html?i="+invitationCode;
        ResultData resultData = new ResultData();
        resultData.setData(uri);
        resultData.setCode(1);
        return resultData;
    }

    /**
     * 当前可以参加的活动
     * @param gameType 游戏类型
     * @return result
     */
    @RequestMapping("/contents")
    public Object contents(String gameType){
        List<Map<String, Object>> result = messageService.selectCanJoinActivity(gameType);
        if(result.isEmpty()){

            return new ResultData("哎呀，今日活动暂未开启，敬请期待！", 0);
        }
        return new ResultData(result);
    }

    /**
     * 当前活动可以参加的优惠
     * @param messageId 活动id
     * @return result
     */
    @RequestMapping("/rules")
    public Object rules(Integer messageId){
        Map map = new HashMap();
        map.put("messageId", messageId);
        map.put("userId", SessionManager.getUserId());
        List<Map<String, Object>> result = relationService.selectCanJoinRelation(map);
        if(result.isEmpty()){

            return new ResultData("哎呀，今日活动优惠暂未开启，敬请期待！", 0);
        }
        return new ResultData(result);
    }

    /**
     * 玩家参加游戏
     * @param relationId 优惠id
     * @param messageId 活动id
     * @return ResultData
     */
    @RequestMapping(value = "/join",method = RequestMethod.POST)
    public Object join(Integer relationId, Integer messageId){
        Date now = DateTool.parseDate(DateTool.currentDate(DateTool.FMT_HYPHEN_DAY),DateTool.FMT_HYPHEN_DAY);

        //判断玩家参加的活动是否为抽奖
        ActivityMessageListVo messageListVo = new ActivityMessageListVo();
        messageListVo.setProperties(ActivityMessage.PROP_ACTIVITY_CLASSIFY_KEY);
        messageListVo.getQuery().setCriterions(new Criterion[]{

            new Criterion(ActivityMessage.PROP_ID, Operator.EQ, messageId)
        });
        List<Map<String, Object>> keyList = messageService.searchProperties(messageListVo);
        String activityKey = (String)keyList.get(0).get("activityClassifyKey");

        ActivityPlayerApplyListVo listVo = new ActivityPlayerApplyListVo();
        listVo.setProperties(ActivityPlayerApply.PROP_ID, ActivityPlayerApply.PROP_IS_REALIZE);

        //抽奖
        if (activityKey.equals("LOTTERY")){

            listVo.getQuery().setCriterions(new Criterion[]{

                    new Criterion(ActivityPlayerApply.PROP_USER_ID, Operator.EQ, SessionManager.getUserId()),
                    new Criterion(ActivityPlayerApply.PROP_ACTIVITY_MESSAGE_ID, Operator.EQ, messageId),
                    new Criterion(ActivityPlayerApply.PROP_ACTIVITY_TYPE_CODE, Operator.EQ, "LOTTERY"),
                    new Criterion(ActivityPlayerApply.PROP_BALANCE_END_TIME, Operator.GE, now),
                    new Criterion(ActivityPlayerApply.PROP_BALANCE_END_TIME, Operator.LE, (DateTool.addDays(now, 1)))
            });
        //其它活动
        }else {

            listVo.getQuery().setCriterions(new Criterion[]{

                    new Criterion(ActivityPlayerApply.PROP_USER_ID, Operator.EQ, SessionManager.getUserId()),
                    new Criterion(ActivityPlayerApply.PROP_ACTIVITY_MESSAGE_ID, Operator.EQ, messageId),
                    new Criterion(ActivityPlayerApply.PROP_ACTIVITY_WAY_RELATION_ID, Operator.EQ, relationId),
                    new Criterion(ActivityPlayerApply.PROP_BALANCE_END_TIME, Operator.GE, now),
                    new Criterion(ActivityPlayerApply.PROP_BALANCE_END_TIME, Operator.LE, (DateTool.addDays(now, 1)))
            });
        }
        List<Map<String, Object>> result = applyService.searchProperties(listVo);
        if (!result.isEmpty()){

            return new ResultData("今日已参加，明天一定要来哦！", 0);
        }else{
            //新增一条数据
            //查出所有的奖品
            ActivityWayRelationListVo relationListVo = new ActivityWayRelationListVo();
            relationListVo.setProperties(ActivityWayRelation.PROP_ID, ActivityWayRelation.PROP_IS_ARTICLE, ActivityWayRelation.PROP_ARTICLE);
            relationListVo.getQuery().setCriterions(new Criterion[]{

                new Criterion(ActivityWayRelation.PROP_ACTIVITY_MESSAGE_ID, Operator.EQ, messageId)
            });
            List<Map<String, Object>> resultList = relationService.searchProperties(relationListVo);
            if (!resultList.isEmpty()){

                //随机取出奖励
                int x = (int)(Math.random()*resultList.size());
                Integer id = (Integer)resultList.get(x).get("id");
                boolean isArticle = (boolean)resultList.get(x).get("isArticle");

                Map map = new HashMap();
                map.put("messageId", messageId);
                map.put("userId", SessionManager.getUserId());
                map.put("userName", SessionManager.getUserName());
                map.put("activityTypeCode","LOTTERY");
                map.put("relationId", id);
                boolean isSuccess = false;

                //如果不是实物，是金币之类的加到玩家相应账号里面
                if (isArticle){

                    map.put("article", (String)resultList.get(x).get("article"));
                    map.put("walletBalance", null);
                    isSuccess = applyService.insertNewRecord(map);
                }else {

                    //查出玩家账户信息
                    UserPlayer userPlayer = playerService.selectUserPlayerInfoById(SessionManager.getUserId());
                    Double walletBalance = userPlayer.getWalletBalance() + (Integer)resultList.get(x).get("article");
                    //修改账户余额
                    map.put("walletBalance", walletBalance);
                    isSuccess = applyService.insertNewRecord(map);
                }
                if (isSuccess){

                    return new ResultData("恭喜获得" + (String)resultList.get(x).get("article") + "奖励！", 1);
                }else {

                    return new ResultData("奖励获得失败！", 0);
                }
            }else {

                return new ResultData("今日未设置奖励！", 0);
            }
        }
    }


}
