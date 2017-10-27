package g.data.activitymessage;

import g.model.activitymessage.po.ActivityMessage;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 游戏信息表数据访问对象
 *
 * @author black
 * @time 2016-9-5 14:46:12
 */
//region your codes 1
public interface ActivityMessageMapper extends IBaseMapper<ActivityMessage, Integer> {


    /**
     * 新增活动信息
     * @param map
     * @return boolean
     */
    boolean insertActivityMessageInfo(Map<String, Object> map);


    /**
     * 修改活动信息
     * @param map
     * @return boolean
     */
    boolean updateActivityMessageInfo(Map<String, Object> map);


    /**
     * 逻辑删除
     * @param map
     * @return
     */
    boolean updateMessageIsDeleted(Map<String, Object> map);


    /**
     * 查询游戏当前可以参加的活动
     * @param gameType
     * @return
     */
    List<Map<String, Object>> selectCanJoinActivity(String gameType);

}