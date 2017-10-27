package g.model.sys.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.sys.po.SysSite;
import g.model.sys.so.SysSiteSo;


/**
 * 站点表值对象
 *
 * @author longer
 * @time Jul 23, 2016 9:50:20 AM
 */
//region your codes 1
public class SysSiteVo extends BaseObjectVo<SysSite, SysSiteSo, SysSiteVo.SysSiteQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 796280985262673235L;
    //endregion your codes 5

    /**
     *  站点表查询逻辑
     */
    public static class SysSiteQuery extends AbstractQuery<SysSiteSo> {

        //region your codes 6
        private static final long serialVersionUID = 3777413993246968394L;
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