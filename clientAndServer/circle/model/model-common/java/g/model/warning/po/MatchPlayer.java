package g.model.warning.po;

import org.soul.commons.bean.IEntity;

import java.util.Date;

/**
 * Created by lenovo on 2017/3/1.
 */
public class MatchPlayer implements IEntity<Long> {
    private static final long serialVersionUID = -6164157438832230046L;

    public static final String PROP_ID = "id";
    public static final String PROP_MATCH_ID = "matchId";
    public static final String PROP_SYS_USER_ID = "sysUserId";
    public static final String PROP_PROFIT_AMOUNT = "profitAmount";
    public static final String PROP_BEGIN_TIME = "beginTime";
    public static final String PROP_END_TIME = "endTime";
    public static final String PROP_EFFECTIVE_AMOUNT = "effectiveAmount";
    public static final String PROP_USERNAME = "username";
    public static final String PROP_NICKNAME = "nickname";
    public static final String PROP_CREATE_TIME = "createTime";

    private Long id;
    private Integer sysUserId;
    private Long matchId;
    private java.util.Date beginTime;
    private java.util.Date endTime;
    private String username;
    private Double profitAmount;
    private Double effectiveAmount;
    private String nickname;
    private java.util.Date createTime;

    public MatchPlayer(){
    }

    public MatchPlayer(Long id){
        this.id = id;
    }
    //endregion


    //region getters and setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(Double profitAmount) {
        this.profitAmount = profitAmount;
    }

    public Double getEffectiveAmount() {
        return effectiveAmount;
    }

    public void setEffectiveAmount(Double effectiveAmount) {
        this.effectiveAmount = effectiveAmount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
