package g.model.admin.vo;


import g.model.admin.po.BetLimit;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.sort.Order;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import org.soul.model.security.privilege.po.SysUser;
import g.model.admin.so.BetLimitSo;

import java.util.List;


/**
 * 投注限额值对象
 *
 * @author tom
 * @time 2016-4-20 11:35:40
 */
//region your codes 1
public class BetLimitVo extends BaseObjectVo<BetLimit, BetLimitSo, BetLimitVo.BetLimitQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -476266227251053287L;
    //endregion your codes 5

    /**
     *  投注限额查询逻辑
     */
    public static class BetLimitQuery extends AbstractQuery<BetLimitSo> {

        //region your codes 6
        private static final long serialVersionUID = -5129970413165005321L;
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
            return new Order[]{Order.asc(BetLimit.PROP_ORDER_NUM)};
        }
        //endregion your codes 3

    }

    //region your codes 4
    // 单注单项限额
    private List<BetLimit> betLimitList;

    // 创建用户
    private SysUser sysUser;

    public List<BetLimit> getBetLimitList() {
        return betLimitList;
    }

    public void setBetLimitList(List<BetLimit> betLimitList) {
        this.betLimitList = betLimitList;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    //endregion your codes 4

}