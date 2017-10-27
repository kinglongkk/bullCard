package g.model.bet.vo;

import g.model.bet.BetSettleStatus;
import g.model.bet.po.VBetDetail;
import g.model.bet.so.VBetDetailSo;
import org.soul.commons.lang.DateTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;

import java.util.Date;

/**
 * 列表页值对象
 *
 * @author mark
 * @time 2016-7-12 14:22:28
 */
public class VBetDetailListVo extends BaseListVo<VBetDetail, VBetDetailSo, VBetDetailListVo.VBetDetailQuery> {

    private static final long serialVersionUID = 3420994912047739740L;

    /**
     *  列表查询逻辑
     */
    public static class VBetDetailQuery extends AbstractQuery<VBetDetailSo> {

        private static final long serialVersionUID = -2331463590140274197L;

        @Override
        public Criteria getCriteria() {

            Criteria criteria = Criteria.and(

                    Criteria.add(VBetDetail.PROP_GAME_ID, Operator.EQ, this.searchObject.getGameId()),
                    Criteria.add(VBetDetail.PROP_SYS_USER_ID, Operator.EQ, this.searchObject.getSysUserId()),
                    Criteria.add(VBetDetail.PROP_CONFIRM_TIME, Operator.GT, this.searchObject.getStartTime()),
                    Criteria.add(VBetDetail.PROP_CONFIRM_TIME, Operator.LT, this.searchObject.getEndTime()),
                    Criteria.add(VBetDetail.PROP_SETTLE_STATUS, Operator.EQ, BetSettleStatus.SETTLED.getCode())
            );
            criteria.addAnd(VBetDetail.PROP_IS_DELETED, Operator.EQ, false);
            return criteria;
        }


        Date startTime = DateTool.parseDate(DateTool.currentDate(DateTool.FMT_HYPHEN_DAY), DateTool.FMT_HYPHEN_DAY);
        Date endTime = DateTool.addDays(startTime, 1);
        public Criteria searchCriteria() {

            Criteria criteria = Criteria.and(

                    Criteria.add(VBetDetail.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(VBetDetail.PROP_OWNER_ID, Operator.EQ, this.searchObject.getOwnerId()),
                    Criteria.add(VBetDetail.PROP_OWNER_USERNAME, Operator.LIKE, this.searchObject.getOwnerUsername()),
                    Criteria.add(VBetDetail.PROP_SYS_USER_ID, Operator.EQ, this.searchObject.getSysUserId()),
                    Criteria.add(VBetDetail.PROP_SYS_USERNAME, Operator.LIKE, this.searchObject.getUsername()),
                    Criteria.add(VBetDetail.PROP_GAME_ID, Operator.EQ, this.searchObject.getGameId()),
                    Criteria.add(VBetDetail.PROP_GAME_MODEL_ID, Operator.EQ, this.searchObject.getGameModelId()),
                    Criteria.add(VBetDetail.PROP_SETTLE_TIME, Operator.GT, startTime),
                    Criteria.add(VBetDetail.PROP_SETTLE_TIME, Operator.LT, endTime),
                    Criteria.add(VBetDetail.PROP_SETTLE_STATUS, Operator.EQ, BetSettleStatus.SETTLED.getCode())
            );
            criteria.addAnd(VBetDetail.PROP_IS_DELETED, Operator.EQ, false);
            return criteria;
        }

        @Override
        public Sort getDefaultSort() {
            return Sort.add(VBetDetail.PROP_ID, Direction.DESC);
        }

    }

    /**
     * 统计页面各类总值 盈亏结果金额(派彩)
     */
    private Double totalProfitAmount;
    /**
     * 统计页面各类总值 下单金额(交易量)
     */
    private Double totalEffectiveAmount;
    /**
     * 统计页面各类总值 实际获利金额
     */
    private Double totalWinAmount;

    public Double getTotalProfitAmount() {
        return totalProfitAmount;
    }

    public void setTotalProfitAmount(Double totalProfitAmount) {
        this.totalProfitAmount = totalProfitAmount;
    }

    public Double getTotalEffectiveAmount() {
        return totalEffectiveAmount;
    }

    public void setTotalEffectiveAmount(Double totalEffectiveAmount) {
        this.totalEffectiveAmount = totalEffectiveAmount;
    }

    public Double getTotalWinAmount() {
        return totalWinAmount;
    }

    public void setTotalWinAmount(Double totalWinAmount) {
        this.totalWinAmount = totalWinAmount;
    }

    /**
     * 页面参数 代理商id
     */
    private Integer ownerId;
    /**
     * 页面参数 玩家id
     */
    private Integer sysUserId;
    /**
     * 页面参数 游戏id
     */
    private Integer gameId;
    /**
     * 页面参数 玩法id
     */
    private Integer gameModelId;

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getGameModelId() {
        return gameModelId;
    }

    public void setGameModelId(Integer gameModelId) {
        this.gameModelId = gameModelId;
    }

}