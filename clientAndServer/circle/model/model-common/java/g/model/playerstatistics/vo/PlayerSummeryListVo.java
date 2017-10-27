package g.model.playerstatistics.vo;


import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.playerstatistics.po.PlayerSummery;
import g.model.playerstatistics.so.PlayerSummerySo;

import java.util.Date;


/**
 * 玩家数据统计表列表页值对象
 *
 * @author lenovo
 * @time 2017-1-5 17:36:23
 */
//region your codes 1
public class PlayerSummeryListVo extends BaseListVo<PlayerSummery, PlayerSummerySo, PlayerSummeryListVo.PlayerSummeryQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7064589258799031398L;
    //endregion your codes 5

    /**
     *  玩家数据统计表列表查询逻辑
     */
    public static class PlayerSummeryQuery extends AbstractQuery<PlayerSummerySo> {

        //region your codes 6
        private static final long serialVersionUID = 323592626250176069L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.and(

                    Criteria.add(PlayerSummery.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(PlayerSummery.PROP_USERNAME, Operator.EQ, this.searchObject.getUsername()),
                    Criteria.add(PlayerSummery.PROP_NEW_PLAYER_QTY, Operator.LIKE, this.searchObject.getNickname()),
                    Criteria.add(PlayerSummery.PROP_DATE, Operator.GE, this.searchObject.getStartTime()),
                    Criteria.add(PlayerSummery.PROP_DATE, Operator.LE, this.searchObject.getEndTime())
            );
            return criteria;
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4
    /**
     * 搜索开始时间
     */
    private Date startTime;
    /**
     * 默认查找时间
     */
    private Date defaultTime;
    /**
     * 默认查找时间
     */
    private String defaultString;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getDefaultTime() {
        return defaultTime;
    }

    public void setDefaultTime(Date defaultTime) {
        this.defaultTime = defaultTime;
    }

    public String getDefaultString() {
        return defaultString;
    }

    public void setDefaultString(String defaultString) {
        this.defaultString = defaultString;
    }

    //endregion your codes 4

}