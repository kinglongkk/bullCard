package g.model.admin.vo;


import g.model.admin.so.VSysAnnouncementSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.po.VSysAnnouncement;


/**
 * 系统公告视图值对象
 *
 * @author orange
 * @time 2015-11-26 20:20:37
 */
//region your codes 1
public class VSysAnnouncementVo extends BaseObjectVo<VSysAnnouncement, VSysAnnouncementSo, VSysAnnouncementVo.VSysAnnouncementQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -7741866266104501766L;
    //endregion your codes 5

    /**
     *  系统公告视图查询逻辑
     */
    public static class VSysAnnouncementQuery extends AbstractQuery<VSysAnnouncementSo> {

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