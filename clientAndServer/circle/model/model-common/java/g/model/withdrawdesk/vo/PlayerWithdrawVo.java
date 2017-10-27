package g.model.withdrawdesk.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.withdrawdesk.po.PlayerWithdraw;
import g.model.withdrawdesk.so.PlayerWithdrawSo;

import java.util.Date;
import java.util.HashMap;


/**
 * 提现记录表值对象
 *
 * @author tom
 * @time 2016-7-13 16:16:43
 */
//region your codes 1
public class PlayerWithdrawVo extends BaseObjectVo<PlayerWithdraw, PlayerWithdrawSo, PlayerWithdrawVo.PlayerWithdrawQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -5395747961722391481L;
    //endregion your codes 5

    /**
     *  提现记录表查询逻辑
     */
    public static class PlayerWithdrawQuery extends AbstractQuery<PlayerWithdrawSo> {

        //region your codes 6
        private static final long serialVersionUID = -724373885091606309L;
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
    /**
     * 自定义username
     */
    private String username;
    //发送消息groupCode
    private String groupCode;
    //交易描述
    private String transactionRemark;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    private Integer playerId;
    private HashMap map;

    public HashMap getMap() {
        return map;
    }

    public void setMap(HashMap map) {
        this.map = map;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTransactionRemark() {
        return transactionRemark;
    }

    public void setTransactionRemark(String transactionRemark) {
        this.transactionRemark = transactionRemark;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }
    //endregion your codes 4

}