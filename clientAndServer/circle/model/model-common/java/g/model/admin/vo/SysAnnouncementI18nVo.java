package g.model.admin.vo;

import g.model.admin.po.SysAnnouncementI18n;
import g.model.admin.so.SysAnnouncementI18nSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;


/**
 * 值对象
 *
 * @author orange
 * @time 2015-11-17 14:59:10
 */
//region your codes 1
public class SysAnnouncementI18nVo extends BaseObjectVo<SysAnnouncementI18n, SysAnnouncementI18nSo, SysAnnouncementI18nVo.SysAnnouncementI18nQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -6890320176388719519L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class SysAnnouncementI18nQuery extends AbstractQuery<SysAnnouncementI18nSo> {

        //region your codes 6
        private static final long serialVersionUID = -6839221683174316991L;
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