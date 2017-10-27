package g.service.announcement;

import g.data.announcement.VGameAnnouncementMapper;
import g.model.announcement.po.VGameAnnouncement;
import g.model.announcement.vo.VGameAnnouncementListVo;
import g.model.announcement.vo.VGameAnnouncementVo;
import g.service.common.IVGameAnnouncementService;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.service.support.BaseService;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016/12/6.
 */
public class VGameAnnounncementService extends BaseService<VGameAnnouncementMapper, VGameAnnouncementListVo, VGameAnnouncementVo, VGameAnnouncement, Integer> implements IVGameAnnouncementService {

    public VGameAnnouncementListVo searchMasterGameNotice(VGameAnnouncementListVo listVo) {
        Criteria criteria = Criteria.add(VGameAnnouncement.PROP_LOCAL, Operator.EQ, listVo.getSearch().getLocal());
        criteria.addAnd(VGameAnnouncement.PROP_ID, Operator.EQ, listVo.getSearch().getId());
        criteria.addAnd(VGameAnnouncement.PROP_PUBLISH_TIME, Operator.GT, listVo.getSearch().getStartTime());
        criteria.addAnd(VGameAnnouncement.PROP_PUBLISH_TIME, Operator.LE, listVo.getSearch().getEndTime());
        criteria.addAnd(VGameAnnouncement.PROP_ANNOUNCEMENT_TYPE, Operator.EQ, listVo.getSearch().getAnnouncementType());
        criteria.addAnd(VGameAnnouncement.PROP_PUBLISH_TIME, Operator.GT, listVo.getSearch().getPublishTime());
        criteria.addAnd(VGameAnnouncement.PROP_PUBLISH_TIME, Operator.LE, new Date());
        listVo.getPaging().setTotalCount(this.mapper.count(criteria));
        listVo.setResult(this.mapper.pagingSearch(criteria, listVo.getPaging().getPageNumber(), listVo.getPaging().getPageSize(), Order.desc(VGameAnnouncement.PROP_ID)));
        listVo.getPaging().cal();
        return listVo;
    }

    @Override
    public List<VGameAnnouncement> searchPersonalAnnouncement(VGameAnnouncementListVo listVo) {
        Criteria criteria = Criteria.add(VGameAnnouncement.PROP_PUBLISH_USER_ID,Operator.EQ,listVo.getSearch().getPublishUserId());
        criteria.addAnd(VGameAnnouncement.PROP_LOCAL, Operator.EQ, listVo.getSearch().getLocal());
        criteria.addAnd(VGameAnnouncement.PROP_ANNOUNCEMENT_TYPE, Operator.EQ, listVo.getSearch().getAnnouncementType());
        return this.mapper.search(criteria);
    }
}
