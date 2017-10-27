package g.model.playerstatistics.vo;

import g.model.UserTypeEnum;
import g.model.playerstatistics.po.PlayerDataStatistics;
import g.model.playerstatistics.so.PlayerDataStatisticsSo;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;

import java.util.Date;

/**
 * 玩家数据统计表列表页值对象
 *
 * @author lenovo
 * @time 2017-1-5 14:06:09
 */
//region your codes 1
public class PlayerDataStatisticsListVo extends BaseListVo<PlayerDataStatistics, PlayerDataStatisticsSo, PlayerDataStatisticsListVo.PlayerDataStatisticsQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 6567138417923654713L;
    //endregion your codes 5

    /**
     *  玩家数据统计表列表查询逻辑
     */
    public static class PlayerDataStatisticsQuery extends AbstractQuery<PlayerDataStatisticsSo> {

        //region your codes 6
        private static final long serialVersionUID = -7551602613494600875L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria =  Criteria.and(

                    Criteria.add(PlayerDataStatistics.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(PlayerDataStatistics.PROP_SYS_USER_ID, Operator.EQ, this.searchObject.getSysUserId()),
                    Criteria.add(PlayerDataStatistics.PROP_GAME_ID, Operator.EQ, this.searchObject.getGameId()),
                    Criteria.add(PlayerDataStatistics.PROP_GAME_NAME, Operator.EQ, this.searchObject.getGameName()),
                    Criteria.add(PlayerDataStatistics.PROP_GAME_MODEL_ID, Operator.EQ, this.searchObject.getGameModelId()),
                    Criteria.add(PlayerDataStatistics.PROP_GAME_MODEL_NAME, Operator.EQ, this.searchObject.getGameModelName()),
                    Criteria.add(PlayerDataStatistics.PROP_ROOM_ID, Operator.EQ, this.searchObject.getRoomId()),
                    Criteria.add(PlayerDataStatistics.PROP_ROOM_NAME, Operator.EQ, this.searchObject.getRoomName()),
                    Criteria.add(PlayerDataStatistics.PROP_BET_DATE, Operator.GE, this.searchObject.getStartTime()),
                    Criteria.add(PlayerDataStatistics.PROP_BET_DATE, Operator.LT, this.searchObject.getEndTime())

            );
            String selectType = this.searchObject.getSelectType();
            if (selectType != null && StringTool.isNotBlank(selectType) && selectType.equals("like")) {
                Criteria.and(
                        criteria.addOr(
                                Criteria.add(PlayerDataStatistics.PROP_USERNAME, Operator.LIKE, this.searchObject.getUsername()),
                                Criteria.add(PlayerDataStatistics.PROP_NICKNAME, Operator.LIKE, this.searchObject.getUsername())
                        )
                );
                if (this.searchObject.getUserType().equals(UserTypeEnum.ADMIN.getCode())) {
                    Criteria.and(
                            criteria.addOr(
                                    Criteria.add(PlayerDataStatistics.PROP_OWNER_USERNAME, Operator.LIKE, this.searchObject.getOwnerUsername()),
                                    Criteria.add(PlayerDataStatistics.PROP_OWNER_USERNAME, Operator.LIKE, this.searchObject.getOwnerUsername())
                            )
                    );
                } else {
                    criteria.addAnd(PlayerDataStatistics.PROP_OWNER_USERNAME, Operator.EQ, this.searchObject.getOwnerUsername());
                }
            } else {
                criteria.addAnd(PlayerDataStatistics.PROP_OWNER_USERNAME, Operator.EQ, this.searchObject.getOwnerUsername());
                criteria.addAnd(PlayerDataStatistics.PROP_USERNAME, Operator.EQ, this.searchObject.getUsername());
            }
            return criteria;
            //endregion your codes 2
        }

        public Sort getDefaultSort(){

            return new Sort(Order.desc(PlayerDataStatistics.PROP_SETTLE_TIME));
        }
        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4
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
     * 页面参数 开始时间
     */
    private Date startTime;
    /**
     * 页面参数 代理用户名
     */
    private String ownerUsername;
    /**
     * 页面参数 玩家用户名
     */
    private Integer playerId;
    /**
     * 页面参数 游戏id
     */
    private Integer gameId;
    /**
     * 页面参数 玩法id
     */
    private Integer gameModelId;
    /**
     * 页面参数 房间id
     */
    private Integer roomId;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
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

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    //endregion your codes 4

}
