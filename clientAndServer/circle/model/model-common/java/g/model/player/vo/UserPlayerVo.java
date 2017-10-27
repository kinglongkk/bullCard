package g.model.player.vo;


import g.model.player.so.UserPlayerSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.player.po.UserPlayer;


/**
 * 会员扩展信息值对象
 *
 * @author tom
 * @time 2016-5-12 11:54:55
 */
//region your codes 1
public class UserPlayerVo extends BaseObjectVo<UserPlayer, UserPlayerSo, UserPlayerVo.UserPlayerQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -3154764699646389214L;
    //endregion your codes 5

    /**
     *  会员扩展信息查询逻辑
     */
    public static class UserPlayerQuery extends AbstractQuery<UserPlayerSo> {

        //region your codes 6
        private static final long serialVersionUID = -4673737499619153825L;
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

    //endregion your codes 4

}