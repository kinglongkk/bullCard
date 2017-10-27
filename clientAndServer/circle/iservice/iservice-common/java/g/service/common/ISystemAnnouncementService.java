package g.service.common;

import g.model.admin.po.SystemAnnouncement;
import g.model.admin.vo.SystemAnnouncementListVo;
import g.model.admin.vo.SystemAnnouncementVo;
import org.soul.iservice.support.IBaseService;


/**
 * 系统公告服务接口
 *
 * @author orange
 * @time 2015-11-17 14:57:53
 */
//region your codes 1
public interface ISystemAnnouncementService extends IBaseService<SystemAnnouncementListVo, SystemAnnouncementVo, SystemAnnouncement, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 运营商发布公告弹窗
     * @param systemAnnouncementVo
     */
    void operatorSendMessageDialog(SystemAnnouncementVo systemAnnouncementVo);

    /**
     * 总控发布公告弹窗
     * @param systemAnnouncementVo
     */
    void centralControlSendMessageDialog(SystemAnnouncementVo systemAnnouncementVo);

    /**
     * 新增公告数量
     * @return
     */
    long searchSystemAnnouncementUnreadCount(SystemAnnouncementVo vo);
    //endregion your codes 2

}