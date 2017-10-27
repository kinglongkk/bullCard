package g.model.player.so;


import g.model.player.po.VSysUser;

import java.util.Date;

/**
 * 玩家交易表查询对象
 * <p/>
 * Created by cheery using soul-code-generator on 2015-6-23 11:41:42
 */
public class VSysUserSo extends VSysUser {

    private static final long serialVersionUID = 8208801112800750592L;

    //region your codes
    /**
     * 搜索开始时间
     */
    private Date startTime;
    /**
     * 搜索结束时间
     */
    private Date endTime;
    /**
     * 玩家用户名
     */
    private String username;
    /**
     * 胜利率
     */
    private Integer winRates;
    /**
     * 连续派彩局数
     */
    private Integer winRound;
    /**
     * 赢得金币与本金倍数比
     */
    private Integer winPercent;
    /**
     * 今日取现次数
     */
    private Integer withdrawCount;
    /**
     * 取现总额
     */
    private Double withdrawTotal;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getWinRates() {
        return winRates;
    }

    public void setWinRates(Integer winRates) {
        this.winRates = winRates;
    }

    public Integer getWinRound() {
        return winRound;
    }

    public void setWinRound(Integer winRound) {
        this.winRound = winRound;
    }

    public Integer getWinPercent() {
        return winPercent;
    }

    public void setWinPercent(Integer winPercent) {
        this.winPercent = winPercent;
    }

    public Integer getWithdrawCount() {
        return withdrawCount;
    }

    public void setWithdrawCount(Integer withdrawCount) {
        this.withdrawCount = withdrawCount;
    }

    @Override
    public Double getWithdrawTotal() {
        return withdrawTotal;
    }

    @Override
    public void setWithdrawTotal(Double withdrawTotal) {
        this.withdrawTotal = withdrawTotal;
    }
    //endregion your codes
}