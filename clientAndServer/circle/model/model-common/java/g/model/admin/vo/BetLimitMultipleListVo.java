package g.model.admin.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import org.soul.model.sys.po.SysParam;
import g.model.ISchemaCode;
import g.model.admin.po.BetLimitMultiple;
import g.model.admin.so.BetLimitMultipleSo;

import java.util.List;
import java.util.Map;


/**
 * 综合投注限额列表页值对象
 *
 * @author tom
 * @time 2016-4-20 11:37:43
 */
//region your codes 1
public class BetLimitMultipleListVo extends BaseListVo<BetLimitMultiple, BetLimitMultipleSo, BetLimitMultipleListVo.BetLimitMultipleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 3637651227192870772L;
    //endregion your codes 5

    /**
     *  综合投注限额列表查询逻辑
     */
    public static class BetLimitMultipleQuery extends AbstractQuery<BetLimitMultipleSo> {

        //region your codes 6
        private static final long serialVersionUID = -723237774608684069L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return Criteria.add(BetLimitMultiple.PROP_SCHEMA_CODE, Operator.EQ, ISchemaCode.SCHEMECODE);
            //endregion your codes 2
        }


        //region your codes 3

        @Override
        public Order[] getOrders() {
            return new Order[]{Order.asc(BetLimitMultiple.PROP_ORDER_NUM)};
        }

        //endregion your codes 3
    }

    //region your codes 4

    private Map<Integer, List<BetLimitMultiple>> betNumMap;

    // 最低投注金额
    private SysParam sysParamBetMin;

    public Map<Integer, List<BetLimitMultiple>> getBetNumMap() {
        return betNumMap;
    }

    public void setBetNumMap(Map<Integer, List<BetLimitMultiple>> betNumMap) {
        this.betNumMap = betNumMap;
    }

    public SysParam getSysParamBetMin() {
        return sysParamBetMin;
    }

    public void setSysParamBetMin(SysParam sysParamBetMin) {
        this.sysParamBetMin = sysParamBetMin;
    }
    //endregion your codes 4

}