package g.model.player.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.player.po.PlayerTransfer;
import g.model.player.so.PlayerTransferSo;


/**
 * 玩家转账记录列表页值对象
 *
 * @author black
 * @time 2016-11-18 17:11:08
 */
//region your codes 1
public class PlayerTransferListVo extends BaseListVo<PlayerTransfer, PlayerTransferSo, PlayerTransferListVo.PlayerTransferQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7444198177111430247L;
    //endregion your codes 5

    /**
     *  玩家转账记录列表查询逻辑
     */
    public static class PlayerTransferQuery extends AbstractQuery<PlayerTransferSo> {

        //region your codes 6
        private static final long serialVersionUID = -5447717203832788193L;
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