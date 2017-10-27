package g.service.common;

import g.model.announcement.po.VGameAnnouncement;
import g.model.announcement.vo.VGameAnnouncementListVo;
import g.model.announcement.vo.VGameAnnouncementVo;
import org.soul.iservice.support.IBaseService;

import java.util.List;


/**
 * 系统公告视图服务接口
 *
 * @author orange
 * @time 2015-11-19 3:00:56
 */
//region your codes 1
public interface IVGameAnnouncementService extends IBaseService<VGameAnnouncementListVo, VGameAnnouncementVo, VGameAnnouncement, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 查询个人公告
     * @param vGameAnnouncementListVo
     * @return
     */
    List<VGameAnnouncement> searchPersonalAnnouncement(VGameAnnouncementListVo vGameAnnouncementListVo);

    //endregion your codes 2

}