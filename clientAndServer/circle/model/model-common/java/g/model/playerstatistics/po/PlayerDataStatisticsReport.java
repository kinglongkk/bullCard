package g.model.playerstatistics.po;

import org.soul.commons.bean.IEntity;

import java.util.Date;

/**
 * Created by lenovo on 2017/3/28.
 * 统计代理商盈亏报表图
 */
public class PlayerDataStatisticsReport implements IEntity<Integer> {

    private static final long serialVersionUID = -6564006963940957815L;

    public static final String PROP_ID = "id";
    public static final String PROP_DATE = "date";
    public static final String PROP_WIN_AMOUNT = "winAmount";
    public static final String PROP_AGENT_USERNAME="agentUsername";

    /** 主键 */
    private Integer id;
    private Date date;
    private Double winAmount;
    private String agentUsername;

    public PlayerDataStatisticsReport(){
    }
    public PlayerDataStatisticsReport(Integer id){
        this.id = id;
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(Double winAmount) {
        this.winAmount = winAmount;
    }

    public String getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String agentUsername) {
        this.agentUsername = agentUsername;
    }
}
