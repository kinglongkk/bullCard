package g.model.admin.gameroomconfig.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.gameroomconfig.po.GameRoomConfigBull100;
import g.model.admin.gameroomconfig.so.GameRoomConfigBull100So;


/**
 * 游戏房间配置信息表值对象
 *
 * @author lenovo
 * @time 2016-12-17 17:20:19
 */
//region your codes 1
public class GameRoomConfigBull100Vo extends BaseObjectVo<GameRoomConfigBull100, GameRoomConfigBull100So, GameRoomConfigBull100Vo.GameRoomConfigBull100Query> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7577185740817285202L;
    //endregion your codes 5

    /**
     *  游戏房间配置信息表查询逻辑
     */
    public static class GameRoomConfigBull100Query extends AbstractQuery<GameRoomConfigBull100So> {

        //region your codes 6
        private static final long serialVersionUID = 87354044407058478L;
        //endregion your codes 6


        public static final String PROP_ID = "id";
        public static final String PROP_ROOM_ID = "roomId";
        public static final String PROP_BET_CHIP = "betChip";
        public static final String PROP_DEALER_BLANCE = "dealerBlance";
        public static final String PROP_DEALER_BLANCE_TIP = "dealerBlanceTip";
        public static final String PROP_DEALER_BLANCE_QUIT = "dealerBlanceQuit";
        //endregion


        //region properties
        /** 主键 */
        private Integer id;
        /** 房间编号 */
        private Integer roomId;
        /** 投注筹码 */
        private String betChip;
        /** 上庄金额 */
        private Integer dealerBlance;
        /** 庄家提醒金额 */
        private Integer dealerBlanceTip;
        /** 庄家下庄金额 */
        private Integer dealerBlanceQuit;

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }

        //region your codes 3

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRoomId() {
            return roomId;
        }

        public void setRoomId(Integer roomId) {
            this.roomId = roomId;
        }

        public String getBetChip() {
            return betChip;
        }

        public void setBetChip(String betChip) {
            this.betChip = betChip;
        }

        public Integer getDealerBlance() {
            return dealerBlance;
        }

        public void setDealerBlance(Integer dealerBlance) {
            this.dealerBlance = dealerBlance;
        }

        public Integer getDealerBlanceTip() {
            return dealerBlanceTip;
        }

        public void setDealerBlanceTip(Integer dealerBlanceTip) {
            this.dealerBlanceTip = dealerBlanceTip;
        }

        public Integer getDealerBlanceQuit() {
            return dealerBlanceQuit;
        }

        public void setDealerBlanceQuit(Integer dealerBlanceQuit) {
            this.dealerBlanceQuit = dealerBlanceQuit;
        }

        //endregion your codes 3

    }

    //region your codes 4

    //endregion your codes 4

}