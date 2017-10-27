package g.model.match.vo;

import g.model.bet.po.VBetList;
import g.model.match.so.VBetListSo;
import org.soul.commons.bean.Pair;
import org.soul.commons.lang.DateTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;

import java.util.ArrayList;
import java.util.List;


/**
 * 注单列表页值对象
 *
 * @author tom
 * @time 2016-6-7 19:50:30
 */
//region your codes 1
public class VBetListListVo extends BaseListVo<VBetList, VBetListSo, VBetListListVo.VBetListQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 4435721959679007517L;
    //endregion your codes 5

    /**
     *  注单列表查询逻辑
     */
    public static class VBetListQuery extends AbstractQuery<VBetListSo> {

        //region your codes 6
        private static final long serialVersionUID = -2763226615237834484L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2

            //日期加一天
            if (searchObject.getBetTimeTo() != null) {
                searchObject.setBetTimeTo(DateTool.addDays(searchObject.getBetTimeTo(),1));
            }
            //日期加一天
            if (searchObject.getBeginTimeTo() != null) {
                searchObject.setBeginTimeTo(searchObject.getBeginTimeTo());
            }

            return Criteria.add(VBetList.PROP_SETTLE_STATUS, Operator.EQ, this.searchObject.getSettleStatus())
                    .addAnd(VBetList.PROP_GAME_TYPE, Operator.EQ, this.searchObject.getGameType())
                    .addAnd(VBetList.PROP_BALL_TYPE, Operator.EQ, this.searchObject.getBallType())
                    .addAnd(VBetList.PROP_BET_TYPE, Operator.EQ, this.searchObject.getBetType())
                    .addAnd(VBetList.PROP_BET_NO, Operator.LIKE, this.searchObject.getBetNo())
                    .addAnd(VBetList.PROP_USERNAME, Operator.LIKE, this.searchObject.getUsername())
                    .addAnd(VBetList.PROP_BEGIN_TIME, Operator.GE, this.searchObject.getBeginTimeFrom())
                    .addAnd(VBetList.PROP_BEGIN_TIME, Operator.LT, this.searchObject.getBeginTimeTo())
                    .addAnd(VBetList.PROP_BET_TIME, Operator.GE, this.searchObject.getBetTimeFrom())
                    .addAnd(VBetList.PROP_BET_TIME, Operator.LT, this.searchObject.getBetTimeTo())
                    .addAnd(VBetList.PROP_OWNER_ID, Operator.EQ, this.searchObject.getOwnerId());
            //endregion your codes 2
        }


        //region your codes 3

        @Override
        public Sort getDefaultSort() {
            return Sort.add(VBetList.PROP_BET_TIME, Direction.DESC);
        }


        //endregion your codes 3
    }

    //region your codes 4
    // 总计下注金额
    private Double totalSingleAmount;
    // 总计有效金额
    private Double totalEffectiveAmount;
    // 总计派彩结果
    private Double totalSendcach;

    public Double getTotalSingleAmount() {
        return totalSingleAmount;
    }

    public void setTotalSingleAmount(Double totalSingleAmount) {
        this.totalSingleAmount = totalSingleAmount;
    }

    public Double getTotalEffectiveAmount() {
        return totalEffectiveAmount;
    }

    public void setTotalEffectiveAmount(Double totalEffectiveAmount) {
        this.totalEffectiveAmount = totalEffectiveAmount;
    }

    public Double getTotalSendcach() {
        return totalSendcach;
    }

    public void setTotalSendcach(Double totalSendcach) {
        this.totalSendcach = totalSendcach;
    }

    public List<Pair> searchList() {
        String vBetList = VBetList.class.getSimpleName();
        List<Pair> searchList = new ArrayList<>();
        searchList.add(new Pair("search." + VBetList.PROP_BET_NO, LocaleTool.tranView("column", vBetList + "." + VBetList.PROP_BET_NO)));
        searchList.add(new Pair("search." + VBetList.PROP_USERNAME, LocaleTool.tranView("column", vBetList + "." + VBetList.PROP_USERNAME)));
        return searchList;
    }

    public List<Pair> searchListForWithdrawdesk() {
        String vBetList = VBetList.class.getSimpleName();
        List<Pair> searchList = new ArrayList<>();
        searchList.add(new Pair("search." + VBetList.PROP_USERNAME, "UserName"));
//        searchList.add(new Pair("search." + VBetList.PROP_BET_NO, LocaleTool.tranView("column", vBetList + "." + VBetList.PROP_BET_NO)));
        return searchList;
    }
    //endregion your codes 4
}