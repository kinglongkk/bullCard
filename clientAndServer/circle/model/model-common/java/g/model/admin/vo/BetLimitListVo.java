package g.model.admin.vo;


import g.model.admin.po.BetLimit;
import g.model.admin.so.BetLimitSo;
import g.model.depositdesk.po.PlayerRecharge;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Order;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import org.soul.model.sys.po.SysParam;
import g.model.admin.po.BetLimitMultiple;

import java.util.List;
import java.util.Map;


/**
 * 投注限额列表页值对象
 *
 * @author tom
 * @time 2016-4-20 11:35:40
 */
//region your codes 1
public class BetLimitListVo extends BaseListVo<BetLimit, BetLimitSo, BetLimitListVo.BetLimitQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 3273028045095955748L;
    //endregion your codes 5

    /**
     *  投注限额列表查询逻辑
     */
    public static class BetLimitQuery extends AbstractQuery<BetLimitSo> {

        //region your codes 6
        private static final long serialVersionUID = -6935977407455336483L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }


        //region your codes 3

        @Override
        public Sort getDefaultSort() {
            return Sort.add(BetLimit.PROP_ORDER_NUM, Direction.ASC);
        }

//        @Override
//        public Order[] getOrders() {
//            return new Order[]{Order.asc(BetLimit.PROP_ORDER_NUM)};
//        }
        //endregion your codes 3
    }

    //region your codes 4
    // 系统设置单注单项集合
    private List<BetLimit> systemBetLimit;
    // key 是betNum ,value 是对应综合投注集合
    private Map<Integer, List<BetLimitMultiple>> betNumKeyMap;
    // 单注最高可赢金额
    private SysParam sysParamWinMax;
    // 最低投注金额
    private SysParam sysParamBetMin;

    public List<BetLimit> getSystemBetLimit() {
        return systemBetLimit;
    }

    public void setSystemBetLimit(List<BetLimit> systemBetLimit) {
        this.systemBetLimit = systemBetLimit;
    }

    public Map<Integer, List<BetLimitMultiple>> getBetNumKeyMap() {
        return betNumKeyMap;
    }

    public void setBetNumKeyMap(Map<Integer, List<BetLimitMultiple>> betNumKeyMap) {
        this.betNumKeyMap = betNumKeyMap;
    }

    public SysParam getSysParamWinMax() {
        return sysParamWinMax;
    }

    public void setSysParamWinMax(SysParam sysParamWinMax) {
        this.sysParamWinMax = sysParamWinMax;
    }

    public SysParam getSysParamBetMin() {
        return sysParamBetMin;
    }

    public void setSysParamBetMin(SysParam sysParamBetMin) {
        this.sysParamBetMin = sysParamBetMin;
    }
//endregion your codes 4

}