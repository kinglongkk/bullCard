
package g.model.admin.vo;

import g.model.admin.po.Remark;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.so.RemarkSo;


/**
 * 列表页值对象
 * <p/>
 * Created by orange using soul-code-generator on 2015-6-30 11:35:38
 */
public class RemarkListVo extends BaseListVo<Remark, RemarkSo, RemarkListVo.PlayerRemarkQuery> {

    private static final long serialVersionUID = 2803905099203851776L;

    /**
     * 列表查询逻辑
     */
    public static class PlayerRemarkQuery extends AbstractQuery<RemarkSo> {

        private static final long serialVersionUID = -9223372036854775808L;

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.add(Remark.PROP_REMARK_TYPE, Operator.EQ, this.searchObject.getRemarkType());
            criteria.addAnd(Remark.PROP_MODEL, Operator.EQ, this.searchObject.getModel());
            criteria.addAnd(Remark.PROP_ENTITY_ID, Operator.EQ, this.searchObject.getEntityId());
            if (searchObject.getEntityUserId() != null && searchObject.getOperatorId() != null) {
                criteria.addAnd(Criteria.or(new Criterion[]{new Criterion(Remark.PROP_ENTITY_USER_ID, Operator.EQ, searchObject.getEntityUserId()),
                        new Criterion(Remark.PROP_OPERATOR_ID, Operator.EQ, searchObject.getOperatorId())}));
            } else if (searchObject.getEntityUserId() != null && searchObject.getOperatorId() == null) {
                criteria.addAnd(Remark.PROP_ENTITY_USER_ID, Operator.EQ, searchObject.getEntityUserId());
            } else if (searchObject.getOperatorId() != null && searchObject.getEntityUserId() == null) {
                criteria.addAnd(Remark.PROP_OPERATOR_ID, Operator.EQ, searchObject.getOperatorId());
            }
            return criteria;
            //endregion your codes 2
        }

        @Override
        public Sort getDefaultSort() {
            Sort sort = new Sort(Order.desc(Remark.PROP_REMARK_TIME));
            return sort;
        }
    }

}