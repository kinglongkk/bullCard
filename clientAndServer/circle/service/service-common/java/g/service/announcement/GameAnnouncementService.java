package g.service.announcement;

import g.data.announcement.GameAnnouncementMapper;
import g.model.announcement.po.GameAnnouncement;
import g.model.announcement.vo.GameAnnouncementListVo;
import g.model.announcement.vo.GameAnnouncementVo;
import org.soul.service.support.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 游戏公告表服务
 *
 * @author lenovo
 * @time 2016-10-28 10:53:18
 */
//region your codes 1
public class GameAnnouncementService extends BaseService<GameAnnouncementMapper, GameAnnouncementListVo, GameAnnouncementVo, GameAnnouncement, Integer> implements IGameAnnouncementService {
//endregion your codes 1

    //region your codes 2
    @Transactional
    public boolean insertGameAnnoucement(Map<String, Object> map){

        return this.mapper.insertGameAnnoucement(map);

    }

    public List<Map<String, Object>> selectGameNotify(GameAnnouncementListVo  listVo) {

        Map<String, Object> queryParams = listVo.getQueryParams();
        return this.mapper.selectGameNotify(queryParams);
    }

    public GameAnnouncementListVo selectGameAnnouncement(GameAnnouncementListVo  listVo){

        Map<String, Object> queryParams = listVo.getQueryParams(listVo.getQuery().searchCriteria());
        listVo.setResult(this.mapper.selectGameAnnouncement(queryParams));
        return  listVo;
    }
    @Override
    public GameAnnouncementListVo selectPassiveMsg(Date publistTime, int isSend, String msgType) {
        GameAnnouncementListVo listVo = new GameAnnouncementListVo();
        Map<String, Object> queryParams = listVo.getQueryParams();
        listVo.setResult(this.mapper.selectPassiveMsg(publistTime,isSend,msgType));
        return listVo;
    }

    /**
     * 查询某个时间段发送的总数
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return
     */
    public int selectCountByPublishTime(Date startTime,Date endTime){

        return this.mapper.selectCountByPublishTime(startTime,endTime);
    }

    //endregion your codes 2

}