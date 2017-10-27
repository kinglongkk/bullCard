package g.web.player.interceptor;

import g.model.activityapply.po.ActivityPlayerApply;
import g.model.activityapply.vo.ActivityPlayerApplyVo;
import g.model.activitymessage.vo.ActivityMessageVo;
import g.model.activitymessage.vo.ActivityWayRelationVo;
import g.model.depositdesk.vo.PlayerRechargeVo;
import g.service.activityapply.ActivityPlayerApplyService;
import g.service.activitymessage.ActivityMessageService;
import g.service.activitymessage.ActivityWayRelationService;
import g.service.depositdesk.PlayerRechargeService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.service.security.privilege.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class ActivityInterceptor {

    @Autowired
    private ActivityPlayerApplyService activityPlayerApplyService;

    @Autowired
    private ActivityMessageService activityMessageService;

    @Autowired
    private ActivityWayRelationService activityWayRelationService;

    @Autowired
    private PlayerRechargeService playerRechargeService;

    @Autowired
    private SysUserService sysUserService;

    @After("execution(public org.soul.model.pay.vo.OnlinePayVo " +
            "org.soul.service.pay.OnlinePayService.callPay(org.soul.model.pay.vo.OnlinePayVo))")
    public void onPaySuccess(ProceedingJoinPoint point) {

        // 需要的参数 start
        int relationId = 0;
        long rechargeId = 0;
        int userId = 0;
        int relationPlayerId = 0;

        // 需要的参数 end


        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.getSearch().setId(userId);
        sysUserService.get(sysUserVo);


        if (relationId > 0) {

            ActivityWayRelationVo activityWayRelationVo = new ActivityWayRelationVo();
            activityWayRelationVo.getSearch().setId(relationId);
            activityWayRelationService.get(activityWayRelationVo);

            ActivityMessageVo activityMessageVo = new ActivityMessageVo();
            activityMessageVo.getSearch().setId(activityWayRelationVo.getResult().getActivityMessageId());
            activityMessageService.get(activityMessageVo);


            if (activityWayRelationVo.getResult() != null && !activityWayRelationVo.getResult().getIsArticle()) {

                double preferentialValue = activityWayRelationVo.getResult().getPreferentialValue();
                double preferentialRatio = activityWayRelationVo.getResult().getPreferentialRatio();

                PlayerRechargeVo playerRechargeVo = new PlayerRechargeVo();
                playerRechargeVo.getSearch().setId(rechargeId);
                playerRechargeService.get(playerRechargeVo);

                Double rechargeAmount = playerRechargeVo.getResult().getRechargeAmount();

                if (rechargeAmount >= preferentialValue) {

                    double preferentialAmount = preferentialValue * preferentialRatio;

                    ActivityPlayerApplyVo activityPlayerApplyVo = new ActivityPlayerApplyVo();
                    activityPlayerApplyVo.getResult().setUserName(sysUserVo.getResult().getUsername());
                    activityPlayerApplyVo.getResult().setIsEffective(true);
                    activityPlayerApplyVo.getResult().setActivityMessageId(activityMessageVo.getResult().getId());
                    activityPlayerApplyVo.getResult().setActivityTypeCode(activityMessageVo.getResult().getActivityTypeCode());
                    activityPlayerApplyVo.getResult().setApplyTime(new Date());
                    activityPlayerApplyVo.getResult().setBalanceStartTime(activityMessageVo.getResult().getStartTime());
                    activityPlayerApplyVo.getResult().setBalanceEndTime(activityMessageVo.getResult().getEndTime());
                    activityPlayerApplyVo.getResult().setIsRealize(true);
                    activityPlayerApplyVo.getResult().setPlayerRechargeId((int) rechargeId);
                    activityPlayerApplyVo.getResult().setUserId(userId);
                    activityPlayerApplyVo.getResult().setPreferentialValue(preferentialAmount);

                    activityPlayerApplyService.insert(activityPlayerApplyVo);

                }
            } else {

                ActivityPlayerApplyVo activityPlayerApplyVo = new ActivityPlayerApplyVo();
                activityPlayerApplyVo.getResult().setUserName(sysUserVo.getResult().getUsername());
                activityPlayerApplyVo.getResult().setIsEffective(true);
                activityPlayerApplyVo.getResult().setActivityMessageId(activityMessageVo.getResult().getId());
                activityPlayerApplyVo.getResult().setActivityTypeCode(activityMessageVo.getResult().getActivityTypeCode());
                activityPlayerApplyVo.getResult().setApplyTime(new Date());
                activityPlayerApplyVo.getResult().setBalanceStartTime(activityMessageVo.getResult().getStartTime());
                activityPlayerApplyVo.getResult().setBalanceEndTime(activityMessageVo.getResult().getEndTime());
                activityPlayerApplyVo.getResult().setIsRealize(true);
                activityPlayerApplyVo.getResult().setUserId(userId);
                activityPlayerApplyVo.getResult().setPreferentialValue(0D);
                activityPlayerApplyVo.getResult().setArticle(activityWayRelationVo.getResult().getArticle());
                activityPlayerApplyService.insert(activityPlayerApplyVo);

            }

        } else {

            ActivityPlayerApplyVo activityPlayerApplyVo = new ActivityPlayerApplyVo();
            activityPlayerApplyVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(ActivityPlayerApply.PROP_RELATION_PLAYER_ID, Operator.EQ,userId),
                new Criterion(ActivityPlayerApply.PROP_IS_REALIZE, Operator.EQ,false),
            });
            activityPlayerApplyService.search(activityPlayerApplyVo);
            if(activityPlayerApplyVo.getResult() == null || activityPlayerApplyVo.getResult().getId() != null){

                ActivityPlayerApplyVo saveActivityPlayerApplyVo = new ActivityPlayerApplyVo();
                saveActivityPlayerApplyVo.getResult().setUserName(sysUserVo.getResult().getUsername());
                saveActivityPlayerApplyVo.getResult().setIsEffective(true);
                saveActivityPlayerApplyVo.getResult().setActivityMessageId(activityPlayerApplyVo.getResult().getId());
                saveActivityPlayerApplyVo.getResult().setActivityTypeCode(activityPlayerApplyVo.getResult().getActivityTypeCode());
                saveActivityPlayerApplyVo.getResult().setApplyTime(new Date());
                saveActivityPlayerApplyVo.getResult().setBalanceStartTime(activityPlayerApplyVo.getResult().getStarttime());
                saveActivityPlayerApplyVo.getResult().setBalanceEndTime(activityPlayerApplyVo.getResult().getEndtime());
                saveActivityPlayerApplyVo.getResult().setIsRealize(true);
                saveActivityPlayerApplyVo.getResult().setUserId(userId);
                saveActivityPlayerApplyVo.getResult().setPreferentialValue(0D);
                activityPlayerApplyService.insert(activityPlayerApplyVo);
            }
        }

    }
}
