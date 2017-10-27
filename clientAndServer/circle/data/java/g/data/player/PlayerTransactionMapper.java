package g.data.player;

import org.soul.data.rdb.mybatis.IBaseMapper;
import g.model.player.po.PlayerTransaction;

import java.util.List;
import java.util.Map;

/**
 * 玩家交易表数据访问对象
 * <p>
 * Created by cheery using soul-code-generator on 2015-6-23 11:41:42
 */
public interface PlayerTransactionMapper extends IBaseMapper<PlayerTransaction, Long> {

    //region your codes

    /**
     * 用户id
     * 确认转账金额
     * @param playerTransaction
     * @return
     */
    boolean entWalletBalance(PlayerTransaction playerTransaction);

    int insertPlayerTransaction(PlayerTransaction playerTransaction);

    /**
     * 更新交易订单（balance通过实时查询user_player的wallet）
     *
     * @param playerTransaction
     * @return
     */
    int updateTransaction(PlayerTransaction playerTransaction);

    /**
     * 玩家账目记录
     * @param map
     * @return
     */
    List<Map<String, Object>> selectPlayerAccountRecord(Map map);

    /**
     * 查找玩家账目记录总条数
     * @param map
     * @return
     */
    Integer selectPlayerAccountRecordNumber(Map map);

    /**
     * 更新玩家的最大充值金额
     * @param map
     * @return
     */
    boolean updateRechargeMaxAmount(Map map);

    /**
     * 修改玩家冻结资金
     * @param playerTransaction
     * @return
     */
    boolean updateFreezingFundsBalance(PlayerTransaction playerTransaction);

    /**
     * 修改玩家交易状态
     * 服务端拒绝操作
     * @param playerTransaction
     * @return
     */
    boolean updatePlayerTransactionStatus(PlayerTransaction playerTransaction);

    /**
     * 通过交易号查询交易信息
     * @param transactionNo
     * @return
     */
    List<PlayerTransaction> selectByTransactionNo(String transactionNo);
    //endregion your codes

}