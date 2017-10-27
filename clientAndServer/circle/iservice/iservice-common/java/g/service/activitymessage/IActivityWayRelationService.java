package g.service.activitymessage;

import org.soul.iservice.support.IBaseService;
import g.model.activitymessage.po.ActivityWayRelation;
import g.model.activitymessage.vo.ActivityWayRelationListVo;
import g.model.activitymessage.vo.ActivityWayRelationVo;

import java.util.List;
import java.util.Map;


/**
 * 活动优惠方式表服务接口
 *
 * @author lenovo
 * @time 2016-9-5 15:04:06
 */
//region your codes 1
public interface IActivityWayRelationService extends IBaseService<ActivityWayRelationListVo, ActivityWayRelationVo, ActivityWayRelation, Integer> {
//endregion your codes 1

    //region your codes 2
    ActivityWayRelation checkCapacityDNShareActivity(Integer userId);


    /**
     * 查询活动当前可以参加的优惠
     * @param map
     * @return
     */
    List<Map<String, Object>> selectCanJoinRelation(Map<String, Object> map);

}