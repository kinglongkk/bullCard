package g.model.sys.vo;


import g.model.common.Const;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.sys.po.SysSite;
import g.model.sys.so.SysSiteSo;



/**
 * 站点表列表页值对象
 *
 * @author longer
 * @time Jul 23, 2016 9:50:20 AM
 */
//region your codes 1
public class SysSiteListVo extends BaseListVo<SysSite, SysSiteSo, SysSiteListVo.SysSiteQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -460333850717228068L;
    //endregion your codes 5

    /**
     *  站点表列表查询逻辑
     */
    public static class SysSiteQuery extends AbstractQuery<SysSiteSo> {

        //region your codes 6
        private static final long serialVersionUID = -5447710663791945791L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {

           Criteria criteria = Criteria.and(
                   Criteria.add(SysSite.PROP_ID, Operator.EQ, Const.Default_Site_Id)
           );
            return criteria;
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}