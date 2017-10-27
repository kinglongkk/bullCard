package g.data.activityapply;

import g.model.activityapply.po.ActivityPlayerApply;
import org.soul.data.rdb.mybatis.IBaseMapper;
import java.util.List;
import java.util.Map;


/**
 * 活动申请玩家表数据访问对象
 *
 * @author black
 * @time 2016-9-18 19:10:57
 */
//region your codes 1
public interface ActivityPlayerApplyMapper extends IBaseMapper<ActivityPlayerApply, Integer> {
//endregion your codes 1


    /**
     * 查询参与统计信息 分组
     * @param queryMap
     * @return
     */
    List<ActivityPlayerApply> selectActivityPlayerApplyInfoByGroup(Map<String, Object> queryMap);

    /**
     * 查询参与统计信息总条数 分组
     * @param queryMap
     * @return
     */
    Integer selectTotalRecordByGroup(Map<String, Object> queryMap);

    /**
     * 查询参与统计信息 详细
     * @param queryMap
     * @return
     */
    List<ActivityPlayerApply> selectActivityPlayerApplyInfoByDetail(Map<String, Object> queryMap);

    /**
     * 查询参与统计信息总条数 详细
     * @param queryMap
     * @return
     */
    Integer selectTotalRecordByDetail(Map<String, Object> queryMap);


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