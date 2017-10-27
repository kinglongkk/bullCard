package g.model.warning.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.warning.po.VWarningPlayerDetail;
import g.model.warning.so.VWarningPlayerDetailSo;


/**
 * 值对象
 *
 * @author lenovo
 * @time 2017-2-28 11:03:58
 */
//region your codes 1
public class VWarningPlayerDetailVo extends BaseObjectVo<VWarningPlayerDetail, VWarningPlayerDetailSo, VWarningPlayerDetailVo.VWarningPlayerDetailQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -2468963446799351333L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class VWarningPlayerDetailQuery extends AbstractQuery<VWarningPlayerDetailSo> {

        //region your codes 6
        private static final long serialVersionUID = -6957491580878026447L;
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