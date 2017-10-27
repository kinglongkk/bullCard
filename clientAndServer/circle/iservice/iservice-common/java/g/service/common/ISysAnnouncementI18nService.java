package g.service.common;

import org.soul.iservice.support.IBaseService;
import g.model.admin.po.SysAnnouncementI18n;
import g.model.admin.vo.SysAnnouncementI18nListVo;
import g.model.admin.vo.SysAnnouncementI18nVo;


/**
 * 服务接口
 *
 * @author orange
 * @time 2015-11-17 14:59:10
 */
//region your codes 1
public interface ISysAnnouncementI18nService extends IBaseService<SysAnnouncementI18nListVo, SysAnnouncementI18nVo, SysAnnouncementI18n, Integer> {
//endregion your codes 1

    //region your codes 2
    Integer deleteI18nSysAnnouncementId(SysAnnouncementI18nVo vo);
    //endregion your codes 2

}