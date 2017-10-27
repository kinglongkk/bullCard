package g.model.player.so;


import g.model.player.po.PlayerTransaction;

import java.util.Date;

/**
 * 玩家交易表查询对象
 * <p/>
 * Created by cheery using soul-code-generator on 2015-6-23 11:41:42
 */
public class PlayerTransactionSo extends PlayerTransaction {

    private static final long serialVersionUID = 8208801112800750592L;

    //region your codes
    private Date startTime;
    private Date endTime;

    public void setStartTime(Date startTime) {this.startTime = startTime;}
    public void setEndTime(Date endTime) {this.endTime = endTime;}
    public Date getStartTime() {return startTime;}
    public Date getEndTime() {return endTime;}
    //endregion your codes
}