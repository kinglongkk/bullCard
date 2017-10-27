package g.model.announcement.vo;

import g.model.announcement.po.VGameAnnouncement;
import g.model.announcement.so.VGameAnnouncementSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;


/**
 * 系统公告视图值对象
 *
 * @author orange
 * @time 2015-11-26 20:20:37
 */
//region your codes 1
public class VGameAnnouncementVo extends BaseObjectVo<VGameAnnouncement, VGameAnnouncementSo, VGameAnnouncementVo.VGameAnnouncementQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -7741866266104501766L;
    //endregion your codes 5

    /**
     *  游戏公告视图查询逻辑
     */
    public static class VGameAnnouncementQuery extends AbstractQuery<VGameAnnouncementSo> {

        //region your codes 6
        private static final long serialVersionUID = 3523458117597241369L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }

        //region your codes 3

        //endregion your codes 3

    }

    //region your codes 4

    //endregion your codes 4

}