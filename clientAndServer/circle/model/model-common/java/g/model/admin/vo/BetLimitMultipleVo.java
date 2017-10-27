package g.model.admin.vo;

import g.model.admin.so.BetLimitMultipleSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.sort.Order;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import org.soul.model.security.privilege.po.SysUser;
import g.model.admin.po.BetLimitMultiple;

import java.util.List;


/**
 * 综合投注限额值对象
 *
 * @author tom
 * @time 2016-4-20 11:37:43
 */
//region your codes 1
public class BetLimitMultipleVo extends BaseObjectVo<BetLimitMultiple, BetLimitMultipleSo, BetLimitMultipleVo.BetLimitMultipleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -5092775287114266400L;
    //endregion your codes 5

    /**
     *  综合投注限额查询逻辑
     */
    public static class BetLimitMultipleQuery extends AbstractQuery<BetLimitMultipleSo> {

        //region your codes 6
        private static final long serialVersionUID = 1549760864155780794L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
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
    // 综合过关单注最高限额
    private List<BetLimitMultiple> betLimitMultipleList;

    private SysUser sysUser;

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public List<BetLimitMultiple> getBetLimitMultipleList() {
        return betLimitMultipleList;
    }

    public void setBetLimitMultipleList(List<BetLimitMultiple> betLimitMultipleList) {
        this.betLimitMultipleList = betLimitMultipleList;
    }
//endregion your codes 4

}