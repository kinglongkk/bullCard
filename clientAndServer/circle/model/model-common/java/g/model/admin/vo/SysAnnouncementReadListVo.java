package g.model.admin.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.po.SysAnnouncementRead;
import g.model.admin.so.SysAnnouncementReadSo;


/**
 * Sys_Announcement_read表
 *
 * @author orange
 * @time 2016-04-12 15:46:10
 */
//region your codes 1
public class SysAnnouncementReadListVo extends BaseListVo<SysAnnouncementRead, SysAnnouncementReadSo, SysAnnouncementReadListVo.SysAnnouncementReadQuery> {
//endregion your codes 1

    //region your codes 5
    //endregion your codes 5

    /**
     *  player_advisory_read表列表查询逻辑
     */
    public static class SysAnnouncementReadQuery extends AbstractQuery<SysAnnouncementReadSo> {

        //region your codes 6
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