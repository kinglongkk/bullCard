package g.service.activityapply;

import org.soul.iservice.support.IBaseService;
import g.model.activityapply.po.ActivityPlayerApply;
import g.model.activityapply.vo.ActivityPlayerApplyListVo;
import g.model.activityapply.vo.ActivityPlayerApplyVo;
import java.util.List;
import java.util.Map;


/**
 * 活动申请玩家表服务接口
 *
 * @author black
 * @time 2016-9-18 19:10:57
 */
//region your codes 1
public interface IActivityPlayerApplyService extends IBaseService<ActivityPlayerApplyListVo, ActivityPlayerApplyVo, ActivityPlayerApply, Integer> {
//endregion your codes 1


    /**
     * 查询参与统计信息
     * @param listVo
     * @return
     */
    ActivityPlayerApplyListVo selectActivityPlayerApplyInfoByGroup(ActivityPlayerApplyListVo listVo);


    /**
     * 查询参与统计信息 详细
     * @param listVo
     * @return
     */
    ActivityPlayerApplyListVo selectActivityPlayerApplyInfoByDetail(ActivityPlayerApplyListVo listVo);


    /**
     * 玩家已经领取奖励
     * @param applyId
     * @return
     */
    boolean updateIsRealize(Integer applyId);


    /**
     * 玩家参加活动
     * @param map
     * @return
     */
    boolean insertNewRecord(Map<String, Object> map);

}