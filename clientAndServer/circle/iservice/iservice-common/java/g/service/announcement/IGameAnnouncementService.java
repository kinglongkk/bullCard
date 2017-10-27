package g.service.announcement;

import g.model.announcement.po.GameAnnouncement;
import g.model.announcement.vo.GameAnnouncementListVo;
import g.model.announcement.vo.GameAnnouncementVo;
import org.soul.iservice.support.IBaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 游戏公告表服务接口
 *
 * @author lenovo
 * @time 2016-10-28 10:53:18
 */
//region your codes 1
public interface IGameAnnouncementService extends IBaseService<GameAnnouncementListVo, GameAnnouncementVo, GameAnnouncement, Integer> {
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
     * @param listVo
     * @return
     */
    List<Map<String, Object>> selectGameNotify(GameAnnouncementListVo  listVo);
    //endregion your codes 2

    /**
     * 查找游戏公告
     * @param listVo
     * @return
     */
    GameAnnouncementListVo selectGameAnnouncement(GameAnnouncementListVo  listVo);

    /**
     * 查找当前时间需要发送的后台被动产生的消息
     * @param publistTime       当前发送时间节点
     * @param isSend            是否发送
     * @param msgType           消息类型
     * @return
     */
    GameAnnouncementListVo selectPassiveMsg(Date publistTime,int isSend,String msgType);

}