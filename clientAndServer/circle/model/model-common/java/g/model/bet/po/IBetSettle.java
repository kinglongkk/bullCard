package g.model.bet.po;

import java.util.Date;

/**
 * Created by lenovo on 2016/10/17.
 */
public interface IBetSettle {

    Long getId() ;

    boolean isSave();

    Integer getSysUserId() ;

    String getRtype() ;

    String getBetNo() ;

    Double getSingleAmount() ;

    Double getProfitAmount() ;

    Double getEffectiveAmount() ;

    Double getCanWin() ;

    String getStatus() ;

    String getSettleStatus() ;

    Date getBetTime() ;

    Long getMatchId() ;

    Long getBetId() ;

    String getBallType() ;

    Double getPoint() ;

    String getBetType() ;

    String getIorField() ;

    String getRatio() ;

    Integer getHostScore() ;

    Integer getClientScore() ;

    String getStrong() ;

    void setProfitAmount(Double profitAmount);

    void setEffectiveAmount(Double effectiveAmount);

    void setIncBalance(Double incBalance);

    void setPoint(Double point);

    void setIncCoin(Double incCoin);

    void setDealerUserId(Integer dealerUserId);

    void setIncBankerCoin(Double incBankerCoin);


    Long getWaterAmount();

    void setWaterAmount(Long waterAmount);

    /**
     * 抽水点数
     */
    double getWaterPoint();

    /**
     * 设置抽水点数
     * @param waterPoint
     */
    void setWaterPoint(double waterPoint);

    /**
     * 投注结果
     * @param result
     */
    void setResult(String result);
}
