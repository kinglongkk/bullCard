package g.service.activitymessage;

import g.data.activityapply.ActivityPlayerApplyMapper;
import g.data.activitymessage.ActivityMessageMapper;
import g.data.activitymessage.ActivityRuleMapper;
import g.model.activityapply.po.ActivityPlayerApply;
import g.model.activityapply.vo.ActivityPlayerApplyVo;
import g.model.activitymessage.po.ActivityMessage;
import g.model.activitymessage.po.ActivityRule;
import g.model.activitymessage.vo.ActivityMessageVo;
import g.model.activitymessage.vo.ActivityRuleVo;
import g.model.enums.ActivityTypeEnum;
import g.model.enums.ChessCardEnum;
import org.soul.commons.lang.DateTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.service.support.BaseService;
import g.data.activitymessage.ActivityWayRelationMapper;
import g.service.activitymessage.IActivityWayRelationService;
import g.model.activitymessage.po.ActivityWayRelation;
import g.model.activitymessage.vo.ActivityWayRelationListVo;
import g.model.activitymessage.vo.ActivityWayRelationVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 活动优惠方式表服务
 *
 * @author lenovo
 * @time 2016-9-5 15:04:06
 */
//region your codes 1
public class ActivityWayRelationService extends BaseService<ActivityWayRelationMapper, ActivityWayRelationListVo, ActivityWayRelationVo, ActivityWayRelation, Integer> implements IActivityWayRelationService {
//endregion your codes 1

    //region your codes 2

    @Autowired
    private ActivityMessageMapper activityMessageMapper;

    @Autowired
    private ActivityRuleMapper activityRuleMapper;

    @Autowired
    private ActivityPlayerApplyMapper activityPlayerApplyMapper;

    @Override
    public ActivityWayRelation checkCapacityDNShareActivity(Integer userId) {

        ActivityMessageVo.ActivityMessageQuery activityMessageQuery
                = new ActivityMessageVo.ActivityMessageQuery();
        activityMessageQuery.setCriterions(new Criterion[]{
            new Criterion(ActivityMessage.PROP_ENDTIME, Operator.GE, DateTool.formatDate(new Date(),DateTool.FMT_CLN_SECOND)),
            new Criterion(ActivityMessage.PROP_ACTIVITY_TYPE_CODE, Operator.EQ, ActivityTypeEnum.SHARE),
            new Criterion(ActivityMessage.PROP_IS_DISPLAY, Operator.EQ,true),
            new Criterion(ActivityMessage.PROP_IS_DELETED, Operator.EQ, false)
        });

        List<ActivityMessage> activityMessageList = activityMessageMapper.search(activityMessageQuery.getFinalCriteria());
        if(activityMessageList.size() > 0){
            ActivityMessage activityMessage = activityMessageList.get(0);
            ActivityRuleVo.ActivityRuleQuery activityRuleQuery
                    = new ActivityRuleVo.ActivityRuleQuery();
            activityRuleQuery.setCriterions(new Criterion[]{
                new Criterion(ActivityRule.PROP_ACTIVITY_MESSAGE_ID,Operator.EQ,activityMessage.getId()),
                new Criterion(ActivityRule.PROP_GAME_TYPE,Operator.EQ,ChessCardEnum.DOU_NIU.getCode())
            });
            List<ActivityRule> activityRuleList = activityRuleMapper.search(activityRuleQuery.getFinalCriteria());
            if(activityRuleList.size() > 0){
                ActivityRule activityRule = activityRuleList.get(0);
                int limitNumber = activityRule.getLimitNumber();
                if(limitNumber > 0) {
                    ActivityPlayerApplyVo.ActivityPlayerApplyQuery activityPlayerApplyQuery
                            = new ActivityPlayerApplyVo.ActivityPlayerApplyQuery();
                    activityPlayerApplyQuery.setCriterions(new Criterion[]{
                            new Criterion(ActivityPlayerApply.PROP_USER_ID, Operator.EQ, userId),
                            new Criterion(ActivityPlayerApply.PROP_ACTIVITY_TYPE_CODE, Operator.EQ, ActivityTypeEnum.SHARE.getCode()),
                            new Criterion(ActivityPlayerApply.PROP_IS_EFFECTIVE, Operator.EQ, true),
                            new Criterion(ActivityPlayerApply.PROP_IS_REALIZE, Operator.EQ, true),
                            new Criterion(ActivityPlayerApply.PROP_IS_DELETED, Operator.EQ, false)
                    });
                    List<ActivityPlayerApply> activityPlayerApplyList = activityPlayerApplyMapper.search(activityPlayerApplyQuery.getFinalCriteria());
                    if (activityPlayerApplyList.size() >= limitNumber) return null;
                }
                boolean isDemandFirst = activityRule.getIsDemandFirst();
                if(isDemandFirst){
                    ActivityPlayerApplyVo.ActivityPlayerApplyQuery activityPlayerApplyQuery
                            = new ActivityPlayerApplyVo.ActivityPlayerApplyQuery();
                    activityPlayerApplyQuery.setCriterions(new Criterion[]{
                            new Criterion(ActivityPlayerApply.PROP_USER_ID,Operator.EQ,userId),
                            new Criterion(ActivityPlayerApply.PROP_ACTIVITY_TYPE_CODE, Operator.EQ, ActivityTypeEnum.SHARE.getCode()),
                            new Criterion(ActivityPlayerApply.PROP_IS_EFFECTIVE,Operator.EQ,true),
                            new Criterion(ActivityPlayerApply.PROP_IS_REALIZE,Operator.EQ,true),
                            new Criterion(ActivityPlayerApply.PROP_IS_DELETED,Operator.EQ,false)
                    });
                    List<ActivityPlayerApply> activityPlayerApplyList
                            = activityPlayerApplyMapper.search(activityPlayerApplyQuery.getFinalCriteria());
                    if(activityPlayerApplyList.size() == 0) return null;
                }

                ActivityWayRelationVo.ActivityWayRelationQuery activityWayRelationQuery
                        = new ActivityWayRelationVo.ActivityWayRelationQuery();
                activityWayRelationQuery.setCriterions(new Criterion[]{
                    new Criterion(ActivityWayRelation.PROP_ACTIVITY_MESSAGE_ID,Operator.EQ,activityMessage.getId()),
                    new Criterion(ActivityWayRelation.PROP_ACTIVITY_RULE_ID,Operator.EQ,activityRule.getId())
                });
                List<ActivityWayRelation> activityWayRelationList = this.mapper.search(activityWayRelationQuery.getFinalCriteria());
                if(activityWayRelationList.size() > 0){
                    return  activityWayRelationList.get(0);
                }
            }
        }
        return null;
    }

    public List<Map<String, Object>> selectCanJoinRelation(Map<String, Object> map){

        return this.mapper.selectCanJoinRelation(map);
    }

}