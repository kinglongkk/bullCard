package g.model.admin.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import g.model.admin.po.SystemAnnouncement;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.so.SystemAnnouncementSo;

import java.util.Date;


/**
 * 系统公告值对象
 *
 * @author orange
 * @time 2015-11-17 14:57:54
 */
//region your codes 1
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SystemAnnouncementVo extends BaseObjectVo<SystemAnnouncement, SystemAnnouncementSo, SystemAnnouncementVo.SystemAnnouncementQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -3282879981228222054L;
    //endregion your codes 5

    /**
     *  系统公告查询逻辑
     */
    public static class SystemAnnouncementQuery extends AbstractQuery<SystemAnnouncementSo> {

        //region your codes 6
        private static final long serialVersionUID = 2262362345905162859L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.add(SystemAnnouncement.PROP_ANNOUNCEMENT_TYPE, Operator.EQ,searchObject.getAnnouncementType());
            criteria = criteria.addAnd(Criteria.add(SystemAnnouncement.PROP_ANNOUNCEMENT_SUBTYPE,Operator.EQ,searchObject.getAnnouncementSubtype()));
            criteria = criteria.addAnd(Criteria.add(SystemAnnouncement.PROP_API_ID,Operator.EQ,searchObject.getApiId()));
            criteria = criteria.addAnd(Criteria.add(SystemAnnouncement.PROP_GAME_ID,Operator.EQ,searchObject.getGameId()));
            criteria = criteria.addAnd(Criteria.add(SystemAnnouncement.PROP_SITE_ID,Operator.EQ,searchObject.getSiteId()));
            return criteria;
            //endregion your codes 2
        }

        //region your codes 3

        @Override
        public Sort getDefaultSort() {
            return new Sort(Order.desc(SystemAnnouncement.PROP_OPERATE_TIME));
        }

        //endregion your codes 3

    }

    //region your codes 4
    private String[] language;//语言版本
    private String[] title;//按语言版本顺序保存对应的标题
    private String[] content;//按语言版本顺序保存对应的内容
    private Date startTime;
    private Date endTime;

    public String[] getLanguage() {
        return language;
    }

    public void setLanguage(String[] language) {
        this.language = language;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
//endregion your codes 4

}