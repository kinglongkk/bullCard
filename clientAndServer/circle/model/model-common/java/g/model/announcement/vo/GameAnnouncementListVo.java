package g.model.announcement.vo;

import org.soul.commons.lang.DateTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.announcement.po.GameAnnouncement;
import g.model.announcement.so.GameAnnouncementSo;

import java.util.Date;


/**
 * 游戏公告表列表页值对象
 *
 * @author lenovo
 * @time 2016-10-28 10:53:18
 */
//region your codes 1
public class GameAnnouncementListVo extends BaseListVo<GameAnnouncement, GameAnnouncementSo, GameAnnouncementListVo.GameAnnouncementQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -5844451651048960640L;
    //endregion your codes 5

    /**
     *  游戏公告表列表查询逻辑
     */
    public static class GameAnnouncementQuery extends AbstractQuery<GameAnnouncementSo> {

        //region your codes 6
        private static final long serialVersionUID = 285466073615405350L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2

            String dateString = DateTool.formatDate(new Date(), DateTool.FMT_HYPHEN_DAY) + " 23:59:59";
            Date endTime = DateTool.parseDate(dateString, DateTool.FMT_HYPHEN_DAY_CLN_SECOND);

            Criteria criteria = Criteria.and(
                    Criteria.add(GameAnnouncement.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(GameAnnouncement.PROP_VALIDITY_START_TIME, Operator.LE, DateTool.addDays(endTime, -1)),
                    Criteria.add(GameAnnouncement.PROP_VALIDITY_END_TIME, Operator.GT, endTime)
            );
            return criteria;
            //endregion your codes 2
        }
        //region your codes 3

        public Criteria searchCriteria() {
            String dateString = DateTool.formatDate(new Date(), DateTool.FMT_HYPHEN_DAY) + " 23:59:59";
            Date endTime = DateTool.parseDate(dateString, DateTool.FMT_HYPHEN_DAY_CLN_SECOND);

            Criteria criteria = Criteria.and(
                    Criteria.add(GameAnnouncement.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(GameAnnouncement.PROP_MSG_TYPE, Operator.EQ, "BACKSTAGE"),
                    Criteria.add(GameAnnouncement.PROP_VALIDITY_START_TIME, Operator.LE, DateTool.addDays(endTime, -1)),
                    Criteria.add(GameAnnouncement.PROP_VALIDITY_END_TIME, Operator.GT, endTime)
            );
            return criteria;
        }
        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}