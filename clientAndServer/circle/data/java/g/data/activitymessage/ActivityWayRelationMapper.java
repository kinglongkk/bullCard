package g.data.activitymessage;

import g.model.activitymessage.po.ActivityWayRelation;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 活动优惠方式表数据访问对象
 *
 * @author lenovo
 * @time 2016-9-5 15:04:06
 */
//region your codes 1
public interface ActivityWayRelationMapper extends IBaseMapper<ActivityWayRelation, Integer> {
//endregion your codes 1


    /**
     * 查询活动当前可以参加的优惠
     * @param map
     * @return
     */
    List<Map<String, Object>> selectCanJoinRelation(Map<String, Object> map);


}