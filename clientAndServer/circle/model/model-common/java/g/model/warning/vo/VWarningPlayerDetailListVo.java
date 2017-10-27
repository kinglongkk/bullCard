package g.model.warning.vo;


import org.soul.commons.query.Criteria;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.warning.po.VWarningPlayerDetail;
import g.model.warning.so.VWarningPlayerDetailSo;


/**
 * 列表页值对象
 *
 * @author lenovo
 * @time 2017-2-28 11:03:58
 */
//region your codes 1
public class VWarningPlayerDetailListVo extends BaseListVo<VWarningPlayerDetail, VWarningPlayerDetailSo, VWarningPlayerDetailListVo.VWarningPlayerDetailQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 4489329603435700599L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class VWarningPlayerDetailQuery extends AbstractQuery<VWarningPlayerDetailSo> {

        //region your codes 6
        private static final long serialVersionUID = 4108495645150838729L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
//            Criteria criteria = Criteria.and(
//                    Criteria.add(VWarningPlayerDetail.PROP_SYS_USER_ID, Operator.EQ, this.searchObject.getSysUserId())
//            );
            return null;

        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}