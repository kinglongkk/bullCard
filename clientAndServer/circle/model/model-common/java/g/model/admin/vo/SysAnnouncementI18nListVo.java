package g.model.admin.vo;

import g.model.admin.po.SysAnnouncementI18n;
import g.model.admin.so.SysAnnouncementI18nSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 列表页值对象
 *
 * @author orange
 * @time 2015-11-17 14:59:10
 */
//region your codes 1
public class SysAnnouncementI18nListVo extends BaseListVo<SysAnnouncementI18n, SysAnnouncementI18nSo, SysAnnouncementI18nListVo.SysAnnouncementI18nQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 6976519327178796959L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class SysAnnouncementI18nQuery extends AbstractQuery<SysAnnouncementI18nSo> {

        //region your codes 6
        private static final long serialVersionUID = 6982822620733584972L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.add(SysAnnouncementI18n.PROP_LOCAL, Operator.EQ,this.searchObject.getLocal());
            criteria = criteria.addAnd(Criteria.add(SysAnnouncementI18n.PROP_SYS_ANNOUNCEMENT_ID,Operator.EQ,searchObject.getSysAnnouncementId()));
            return criteria;
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}