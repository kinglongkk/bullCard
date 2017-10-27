package g.data.announcement;

import g.model.announcement.po.GameAnnouncement;
import org.apache.ibatis.annotations.Param;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 游戏公告表数据访问对象
 *
 * @author lenovo
 * @time 2016-10-28 10:53:18
 */
//region your codes 1
public interface GameAnnouncementMapper extends IBaseMapper<GameAnnouncement, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 新增游戏公告
     * @param map
     * @return boolean
     */
    boolean insertGameAnnoucement(Map<String, Object> map);

    /**
     * 查找游戏公告
     * @param map
     * @return
     */
    List<Map<String, Object>> selectGameNotify(Map map);


    /**
     * 查找游戏公告
     * @param map
     * @return
     */
    List<GameAnnouncement> selectGameAnnouncement(Map map);

    /**
     * 查找当前需要发送的被动消息
     * @param publistTime       当前时间节点
     * @param isSend            是否需已放松过的消息
     * @param msgType           消息类型
     * @return
     */
    List<GameAnnouncement> selectPassiveMsg(Date publistTime, int isSend, String msgType);


    /**
     * 查询某个时间段的发送总数
     * @param startTime         开始时间
     * @param endTime           结束时间
     * @return
     */
    int selectCountByPublishTime( @Param("startTime") Date startTime, @Param("endTime")Date endTime);
    //endregion your codes 2
}