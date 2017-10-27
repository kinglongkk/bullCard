package g.service.common;

import g.model.admin.vo.VSysAnnouncementVo;
import org.soul.iservice.support.IBaseService;
import g.model.admin.po.VSysAnnouncement;
import g.model.admin.vo.VSysAnnouncementListVo;

import java.util.List;


/**
 * 系统公告视图服务接口
 *
 * @author orange
 * @time 2015-11-19 3:00:56
 */
//region your codes 1
public interface IVSysAnnouncementService extends IBaseService<VSysAnnouncementListVo, VSysAnnouncementVo, VSysAnnouncement, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 查询个人公告
     * @param vSysAnnouncementListVo
     * @return
     */
    List<VSysAnnouncement> searchPersonalAnnouncement(VSysAnnouncementListVo vSysAnnouncementListVo);

    //endregion your codes 2

}