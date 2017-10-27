package g.service.common;

import g.data.admin.VSysAnnouncementMapper;
import g.model.admin.vo.VSysAnnouncementVo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.service.support.BaseService;
import g.model.admin.po.VSysAnnouncement;
import g.model.admin.vo.VSysAnnouncementListVo;

import java.util.Date;
import java.util.List;


/**
 * 系统公告视图服务
 *
 * @author orange
 * @time 2015-11-19 3:00:56
 */
//region your codes 1
public class VSysAnnouncementService extends BaseService<VSysAnnouncementMapper, VSysAnnouncementListVo, VSysAnnouncementVo, VSysAnnouncement, Integer> implements IVSysAnnouncementService {
//endregion your codes 1

    //region your codes 2

    public VSysAnnouncementListVo searchMasterSysNotice(VSysAnnouncementListVo listVo) {
        Criteria criteria = Criteria.add(VSysAnnouncement.PROP_LOCAL, Operator.EQ, listVo.getSearch().getLocal());
        criteria.addAnd(VSysAnnouncement.PROP_ID, Operator.EQ, listVo.getSearch().getId());
        criteria.addAnd(VSysAnnouncement.PROP_PUBLISH_TIME, Operator.GT, listVo.getSearch().getStartTime());
        criteria.addAnd(VSysAnnouncement.PROP_PUBLISH_TIME, Operator.LE, listVo.getSearch().getEndTime());
        criteria.addAnd(VSysAnnouncement.PROP_ANNOUNCEMENT_TYPE, Operator.EQ, listVo.getSearch().getAnnouncementType());
        criteria.addAnd(VSysAnnouncement.PROP_PUBLISH_TIME, Operator.GT, listVo.getSearch().getPublishTime());
        criteria.addAnd(VSysAnnouncement.PROP_PUBLISH_TIME, Operator.LE, new Date());
        listVo.getPaging().setTotalCount(this.mapper.count(criteria));
        listVo.setResult(this.mapper.pagingSearch(criteria, listVo.getPaging().getPageNumber(), listVo.getPaging().getPageSize(), Order.desc(VSysAnnouncement.PROP_ID)));
        listVo.getPaging().cal();
        return listVo;
    }

    @Override
    public List<VSysAnnouncement> searchPersonalAnnouncement(VSysAnnouncementListVo listVo) {
        Criteria criteria = Criteria.add(VSysAnnouncement.PROP_RECIPIENT_USER_ID,Operator.EQ,listVo.getSearch().getRecipientUserId());
        criteria.addAnd(VSysAnnouncement.PROP_LOCAL, Operator.EQ, listVo.getSearch().getLocal());
        criteria.addAnd(VSysAnnouncement.PROP_ANNOUNCEMENT_TYPE, Operator.EQ, listVo.getSearch().getAnnouncementType());
        return this.mapper.search(criteria);
    }

    //endregion your codes 2

}