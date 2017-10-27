package g.model.bet.vo;


import g.model.bet.po.Bet;
import g.model.bet.po.BetDetail;
import g.model.bet.so.BetSo;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;

import java.util.List;


/**
 * 值对象
 *
 * @author longer
 * @time Apr 26, 2016 4:56:53 PM
 */
//region your codes 1
public class BetVo extends BaseObjectVo<Bet, BetSo, BetVo.BetQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7648078622922493174L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class BetQuery extends AbstractQuery<BetSo> {

        //region your codes 6
        private static final long serialVersionUID = -4153359649033687896L;
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

    //余额增加数
    //use in re settle
    private Double singleAmountOld;
    private Double profitAmountOld;

    /**
     * 综合过关最小串
     */
    private Integer minlimit;

    /**
     * 综合过关最大串
     */
    private Integer maxlimit;

    /**
     * 球类
     */
    private String ballType;

    /**
     * 玩家
     */
    private Integer sysUserId;

    private List<BetDetail> betDetailList;


    /**重庆时时彩玩法**/
    private String betItem;


    private BetDetail betDetail;

    private double singleAmount;

    private long matchId;

    /** 是否要保存,ai在系统庄时投注不保存 */
    private boolean isSave = false;

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public List<BetDetail> getBetDetailList() {
        return betDetailList;
    }

    public void setBetDetailList(List<BetDetail> betDetailList) {
        this.betDetailList = betDetailList;
    }

    public Integer getMinlimit() {
        return minlimit;
    }

    public void setMinlimit(Integer minlimit) {
        this.minlimit = minlimit;
    }

    public Integer getMaxlimit() {
        return maxlimit;
    }

    public void setMaxlimit(Integer maxlimit) {
        this.maxlimit = maxlimit;
    }

    public String getBallType() {
        return ballType;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    public Double getSingleAmountOld() {
        return singleAmountOld;
    }

    public void setSingleAmountOld(Double singleAmountOld) {
        this.singleAmountOld = singleAmountOld;
    }

    public Double getProfitAmountOld() {
        return profitAmountOld;
    }

    public void setProfitAmountOld(Double profitAmountOld) {
        this.profitAmountOld = profitAmountOld;
    }

    public BetDetail getBetDetail() {
        return betDetail;
    }

    public void setBetDetail(BetDetail betDetail) {
        this.betDetail = betDetail;
    }

    public String getBetItem() {
        return betItem;
    }

    public void setBetItem(String betItem) {
        this.betItem = betItem;
    }

    public double getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(double singleAmount) {
        this.singleAmount = singleAmount;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    //endregion your codes 4

}