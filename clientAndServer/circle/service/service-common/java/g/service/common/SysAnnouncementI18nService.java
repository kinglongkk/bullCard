package g.service.common;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.service.support.BaseService;
import g.data.admin.SysAnnouncementI18nMapper;
import g.model.admin.po.SysAnnouncementI18n;
import g.model.admin.vo.SysAnnouncementI18nListVo;
import g.model.admin.vo.SysAnnouncementI18nVo;


/**
 * 服务
 *
 * @author orange
 * @time 2015-11-17 14:59:10
 */
//region your codes 1
public class SysAnnouncementI18nService extends BaseService<SysAnnouncementI18nMapper, SysAnnouncementI18nListVo, SysAnnouncementI18nVo, SysAnnouncementI18n, Integer> implements ISysAnnouncementI18nService {
//endregion your codes 1

    //region your codes 2


    @Override
    public Integer deleteI18nSysAnnouncementId(SysAnnouncementI18nVo vo) {
        Criteria criteria = Criteria.add(SysAnnouncementI18n.PROP_LOCAL, Operator.EQ,vo.getSearch().getLocal());
        criteria.addAnd(SysAnnouncementI18n.PROP_SYS_ANNOUNCEMENT_ID, Operator.EQ, vo.getSearch().getSysAnnouncementId());
        return this.mapper.batchDeleteCriteria(criteria);
    }


    //endregion your codes 2

}