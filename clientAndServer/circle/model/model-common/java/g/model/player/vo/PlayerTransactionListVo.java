package g.model.player.vo;

import g.model.TransactionStatusEnum;
import g.model.TransactionTypeEnum;
import g.model.player.so.PlayerTransactionSo;
import org.apache.zookeeper.Transaction;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.player.po.PlayerTransaction;

import java.util.Date;

/**
 * 玩家交易表列表页值对象
 *
 * @author cheery
 * @time 2015-8-17 10:20:34
 */
public class PlayerTransactionListVo extends BaseListVo<PlayerTransaction, PlayerTransactionSo, PlayerTransactionListVo.PlayerTransactionQuery> {

    private static final long serialVersionUID = -5885743353797133959L;
    /**
     *  玩家交易表列表查询逻辑
     */
    public static class PlayerTransactionQuery extends AbstractQuery<PlayerTransactionSo> {

        private static final long serialVersionUID = -4576154452239636167L;


        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.and(
                    Criteria.add(PlayerTransaction.PROP_PLAYER_ID, Operator.EQ, this.searchObject.getPlayerId()),
                    Criteria.add(PlayerTransaction.PROP_STATUS, Operator.EQ, TransactionStatusEnum.SUCCESS.getCode()),
                    Criteria.add(PlayerTransaction.PROP_COMPLETION_TIME, Operator.GT, this.searchObject.getStartTime()),
                    Criteria.add(PlayerTransaction.PROP_COMPLETION_TIME, Operator.LE, this.searchObject.getEndTime()),
                    Criteria.or(
                            Criteria.add(PlayerTransaction.PROP_TRANSACTION_TYPE, Operator.EQ, TransactionTypeEnum.DEPOSIT.getCode()),
                            Criteria.add(PlayerTransaction.PROP_TRANSACTION_TYPE, Operator.EQ, TransactionTypeEnum.WITHDRAWALS.getCode())
                    )
            );
            return criteria;
            //endregion your codes 2
        }
    }
        //region your codes 3
        //endregion your codes 4

}